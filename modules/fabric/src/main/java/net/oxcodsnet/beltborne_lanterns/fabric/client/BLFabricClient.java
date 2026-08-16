package net.oxcodsnet.beltborne_lanterns.fabric.client;

import net.oxcodsnet.beltborne_lanterns.BLMod;
import net.oxcodsnet.beltborne_lanterns.common.config.BLClientConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.oxcodsnet.beltborne_lanterns.common.network.BeltSyncPayload;
import net.oxcodsnet.beltborne_lanterns.common.network.ToggleLanternPayload;
import net.oxcodsnet.beltborne_lanterns.common.client.BLClientAbstractions;
import net.oxcodsnet.beltborne_lanterns.common.client.BLModelLayers;
import net.oxcodsnet.beltborne_lanterns.common.client.ClientBeltPlayers;
import net.oxcodsnet.beltborne_lanterns.common.client.LanternBeltModel;
import net.oxcodsnet.beltborne_lanterns.common.client.LanternClientLogic;
import net.oxcodsnet.beltborne_lanterns.common.client.LanternClientScreens;
// LanternDebugScreen temporarily disabled for MC 26.1 - rendering API needs update
// import net.oxcodsnet.beltborne_lanterns.common.client.ui.LanternDebugScreen;
import net.oxcodsnet.beltborne_lanterns.common.config.BLClientConfigAccess;
import net.oxcodsnet.beltborne_lanterns.common.LampRegistry;
import net.oxcodsnet.beltborne_lanterns.common.network.LampConfigSyncPayload;
import net.oxcodsnet.beltborne_lanterns.common.physics.LanternSwingManager;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.UUID;

public final class BLFabricClient implements ClientModInitializer {
    // Keybindings
    private static final KeyMapping.Category BELTBORNE_CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath(BLMod.MOD_ID, "beltborne_lanterns"));
    private static KeyMapping openConfigKey;
    private static KeyMapping toggleDebugKey;
    private static KeyMapping openDebugEditorKey;
    private static KeyMapping toggleLanternKey;

    @Override
    public void onInitializeClient() {
        // No longer need custom model layer - using ItemStack rendering directly
        // ModelLayerRegistry.registerModelLayer(BLModelLayers.LANTERN_BELT_LAYER, LanternBeltModel::createBodyLayer);
        
        // Load the lamp registry from the client's config file on startup.
        // This makes the config screen work before joining a world.
        LampRegistry.init();
        // Initialize config at startup so renderer uses correct values from config file
        BLClientConfigAccess.get();
        BLMod.LOGGER.info("Client initialization started [Fabric]");

        // Rebuild registry after client joins a server (tags/registries are synced at this point)
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            client.execute(()->{
                ClientBeltPlayers.clear();
                LanternSwingManager.clearAll();
                LampRegistry.init();
            });
        });

        // Clear caches on disconnect as well
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            Minecraft.getInstance().execute(() -> {
                ClientBeltPlayers.clear();
                LanternSwingManager.clearAll();
            });
        });

        // Register network receiver: updates local client set
        ClientPlayNetworking.registerGlobalReceiver(BeltSyncPayload.ID, (payload, context) -> {
            UUID uuid = payload.playerUuid();
            Item lamp = payload.lampId() != null ? BuiltInRegistries.ITEM.getValue(payload.lampId()) : null;
            Minecraft.getInstance().execute(() -> {
                ClientBeltPlayers.setLamp(uuid, lamp);
            });
        });

        // This receiver handles lamp configs sent from a dedicated server.
        ClientPlayNetworking.registerGlobalReceiver(LampConfigSyncPayload.ID, (payload, context) -> {
            Minecraft client = Minecraft.getInstance();
            client.execute(() -> {
                var cliCfg = BLClientConfigAccess.get();
                cliCfg.extraLampLight.clear();
                payload.lamps().forEach((id, lum) -> cliCfg.extraLampLight.add(new BLClientConfig.ExtraLampEntry(id.toString(), lum)));
                BLClientConfigAccess.save();

                // Re-initialize the lamp registry with the new data from the server.
                LampRegistry.init();
            });
        });

        // Clean up swing manager state when a player entity is unloaded
        ClientEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof Player) {
                LanternSwingManager.removePlayer(entity.getUUID());
            }
        });

        // Optionally register dynamic lights for the belt lantern when LambDynamicLights is present
        boolean hasLamb = FabricLoader.getInstance().isModLoaded("lambdynlights");

        // Keybind to open config (default: L) - uses reflection for MC 26.x compatibility
        openConfigKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.beltborne_lanterns.open_config",
                InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L,
                BELTBORNE_CATEGORY
        ));

        // Keybind to toggle debug gizmos (default: K)
        toggleDebugKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.beltborne_lanterns.toggle_debug",
                InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K,
                BELTBORNE_CATEGORY
        ));

        // Keybind to open lantern debug editor (default: P)
        openDebugEditorKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.beltborne_lanterns.open_debug",
                InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P,
                BELTBORNE_CATEGORY
        ));

        // Keybind to toggle belt lantern (default: B)
        toggleLanternKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.beltborne_lanterns.toggle_lantern",
                InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B,
                BELTBORNE_CATEGORY
        ));

        // Wire platform abstractions so common renderer can query state/debug
        BLClientAbstractions.init(ClientBeltPlayers::getLamp);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openConfigKey != null && openConfigKey.consumeClick()) {
                try {
                    // Try reflection to check current screen (MC 26.1 has 'screen' field, 26.2 removed it)
                    boolean noScreenOpen = true;
                    try {
                        var f = Minecraft.class.getDeclaredField("screen");
                        f.setAccessible(true);
                        noScreenOpen = f.get(client) == null;
                    } catch (NoSuchFieldException ignored) { /* MC 26.2+ - assume no screen open */ }
                    
                    if (noScreenOpen) {
                        var screen = LanternClientScreens.openConfig(null);
                        // Try setScreen via reflection for MC 26.2 compatibility
                        try {
                            var m = Minecraft.class.getMethod("setScreen", net.minecraft.client.gui.screens.Screen.class);
                            m.invoke(client, screen);
                        } catch (NoSuchMethodException e2) {
                            // setScreen renamed in 26.2 - try alternative names
                            for (var m : Minecraft.class.getMethods()) {
                                if (m.getParameterCount() == 1 && 
                                    m.getParameterTypes()[0].getName().contains("Screen")) {
                                    m.invoke(client, screen);
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {
                    // Config still accessible via ModMenu
                }
            }

            if (toggleDebugKey.consumeClick()) {
                if (net.oxcodsnet.beltborne_lanterns.common.config.BLClientConfigAccess.get().debug) {
                    BLClientAbstractions.setDebugDrawEnabled(!BLClientAbstractions.isDebugDrawEnabled());
                }
            }

            if (openDebugEditorKey != null && openDebugEditorKey.consumeClick()) {
                if (net.oxcodsnet.beltborne_lanterns.common.config.BLClientConfigAccess.get().debug) {
                    // Debug editor temporarily disabled - screen rendering needs update
                }
            }

            if (toggleLanternKey.consumeClick()) {
                ClientPlayNetworking.send(new ToggleLanternPayload());
            }

            // Update lantern physics states for players who have a belt lantern
            LanternClientLogic.tickLanternPhysics(client);
        });

        // Final client-ready log (concise, useful to players)
        String dyn = hasLamb ? "enabled" : "disabled";
        BLMod.LOGGER.info("Client ready [Fabric]. Dynamic lights: {}.", dyn);
    }

    // Debug flag is maintained via BLClientAbstractions
}
