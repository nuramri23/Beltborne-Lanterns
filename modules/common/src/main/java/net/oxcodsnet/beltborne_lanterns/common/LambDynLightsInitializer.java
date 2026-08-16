package net.oxcodsnet.beltborne_lanterns.common;

import dev.lambdaurora.lambdynlights.api.DynamicLightsContext;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.entity.EntityLightSourceManager;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.oxcodsnet.beltborne_lanterns.BLMod;
import net.oxcodsnet.beltborne_lanterns.common.client.BLClientAbstractions;
import org.jetbrains.annotations.NotNull;

/**
 * Entrypoint for LambDynamicLights 4.x API.
 *
 * @see <a href="https://lambdaurora.dev/projects/lambdynamiclights/docs/v4/java.html#register-an-entity-luminance-provider">
 *     LambDynamicLights 4.x documentation on entity luminance
 * </a>
 */
public final class LambDynLightsInitializer implements DynamicLightsInitializer {
    static final EntityLuminance.Type PLAYER_LANTERN = EntityLuminance.Type.registerSimple(
            Identifier.fromNamespaceAndPath(BLMod.MOD_ID, "player_lantern"),
            PlayerLanternLuminance.INSTANCE
    );

    @Override
    public void onInitializeDynamicLights(DynamicLightsContext context) {
        EntityLightSourceManager mgr = context.entityLightSourceManager();
        mgr.onRegisterEvent().register(ctx -> {
            try {
                // Try static field first (MC 26.1.x)
                @SuppressWarnings("unchecked")
                EntityType<? extends net.minecraft.world.entity.LivingEntity> playerType =
                    (EntityType<? extends net.minecraft.world.entity.LivingEntity>)
                    EntityType.class.getField("PLAYER").get(null);
                ctx.register(playerType, PlayerLanternLuminance.INSTANCE);
                BLMod.LOGGER.info("Dynamic lights: registered via EntityType.PLAYER (MC 26.1.x)");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // MC 26.2+ - use registryAccess() from the event context
                try {
                    var registryAccess = ctx.registryAccess();
                    var registry = registryAccess.lookupOrThrow(net.minecraft.core.registries.Registries.ENTITY_TYPE);
                    var playerKey = net.minecraft.resources.ResourceKey.create(
                        net.minecraft.core.registries.Registries.ENTITY_TYPE,
                        Identifier.fromNamespaceAndPath("minecraft", "player")
                    );
                    registry.get(playerKey).ifPresent(holder -> {
                        @SuppressWarnings("unchecked")
                        var playerType = (EntityType<? extends net.minecraft.world.entity.LivingEntity>) holder.value();
                        ctx.register(playerType, PlayerLanternLuminance.INSTANCE);
                        BLMod.LOGGER.info("Dynamic lights: registered via registryAccess (MC 26.2+ compat)");
                    });
                } catch (Exception e2) {
                    BLMod.LOGGER.warn("Dynamic lights: could not register player entity - {}", e2.getMessage());
                }
            }
        });
        BLMod.LOGGER.info("Dynamic lights: integrated via LambDynamicLights 4.x (API entrypoint)");
        LambDynLightsCompat.markInitialized();
    }

    private final static class PlayerLanternLuminance implements EntityLuminance {
        static final PlayerLanternLuminance INSTANCE = new PlayerLanternLuminance();

        @Override
        public Type type() {
            return PLAYER_LANTERN;
        }

        @Override
        public int getLuminance(@NotNull ItemLightSourceManager itemLightSourceManager, Entity entity) {
            if (entity instanceof Player player) {
                Item lamp = BLClientAbstractions.clientLamp(player);
                return lamp != null ? LampRegistry.getLuminance(lamp) : 0;
            }
            return 0;
        }
    }
}
