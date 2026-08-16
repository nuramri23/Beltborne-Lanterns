package net.oxcodsnet.beltborne_lanterns.common.persistence;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import com.mojang.serialization.Codec;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * World-persistent storage of which players have the belt lantern equipped.
 * Stores full ItemStack NBT to preserve enchantments, names, etc.
 */
public final class BeltLanternSave extends SavedData {
    private static final String PLAYERS_KEY = "players";

    private final Map<UUID, ItemStack> playersWithLamps = new HashMap<>();

    private static final Codec<BeltLanternSave> CODEC = Codec.of(
            // Encoder - must also use anonymous class because encode() is generic
            new com.mojang.serialization.Encoder<BeltLanternSave>() {
                @Override
                public <T> com.mojang.serialization.DataResult<T> encode(
                        BeltLanternSave save, com.mojang.serialization.DynamicOps<T> ops, T prefix) {
                    CompoundTag nbt = new CompoundTag();
                    save.writeNbt(nbt, null);
                    return CompoundTag.CODEC.encodeStart(ops, nbt);
                }
            },
            // Decoder - must use anonymous class because decode() is generic
            new com.mojang.serialization.Decoder<BeltLanternSave>() {
                @Override
                public <T> com.mojang.serialization.DataResult<com.mojang.datafixers.util.Pair<BeltLanternSave, T>> decode(
                        com.mojang.serialization.DynamicOps<T> ops, T input) {
                    return CompoundTag.CODEC.parse(ops, input).map(nbt ->
                            com.mojang.datafixers.util.Pair.of(BeltLanternSave.fromNbt(nbt, null), input)
                    );
                }
            }
    );

    private static final SavedDataType<BeltLanternSave> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath("beltborne_lanterns", "belts"),
            BeltLanternSave::new,
            CODEC,
            null // DataFixTypes - not needed
    );

    public static BeltLanternSave get(MinecraftServer server) {
        var psManager = server.overworld().getDataStorage();
        return psManager.computeIfAbsent(TYPE);
    }

    public BeltLanternSave() {}

    public boolean has(UUID uuid) {
        return playersWithLamps.containsKey(uuid);
    }

    /**
     * Returns the item type of the stored lamp, or null.
     */
    public Item get(UUID uuid) {
        ItemStack stack = playersWithLamps.get(uuid);
        return stack != null ? stack.getItem() : null;
    }

    /**
     * Returns a copy of the stored lamp stack, or null.
     */
    public ItemStack getStack(UUID uuid) {
        ItemStack stack = playersWithLamps.get(uuid);
        return stack != null ? stack.copy() : null;
    }

    /**
     * Persists a full lamp stack (stored as a single-item copy), or clears when null.
     */
    public void set(UUID uuid, ItemStack lamp) {
        if (lamp != null && !lamp.isEmpty()) {
            playersWithLamps.put(uuid, lamp.copyWithCount(1));
        } else {
            playersWithLamps.remove(uuid);
        }
        setDirty();
    }

    /**
     * Convenience setter by item type (no NBT).
     */
    public void set(UUID uuid, Item lamp) {
        if (lamp != null) {
            playersWithLamps.put(uuid, new ItemStack(lamp));
        } else {
            playersWithLamps.remove(uuid);
        }
        setDirty();
    }

    public static BeltLanternSave fromNbt(CompoundTag nbt) {
        BeltLanternSave save = new BeltLanternSave();
        CompoundTag map = nbt.getCompoundOrEmpty(PLAYERS_KEY);
        for (String key : map.keySet()) {
            try {
                UUID uuid = UUID.fromString(key);
                Tag el = map.get(key);
                if (el != null && !(el instanceof StringTag)) {
                    ItemStack.CODEC.parse(NbtOps.INSTANCE, el).result().ifPresent(stack -> {
                        if (!stack.isEmpty()) {
                            save.playersWithLamps.put(uuid, stack);
                        }
                    });
                }
            } catch (IllegalArgumentException ignored) {}
        }
        return save;
    }

    public static BeltLanternSave fromNbt(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        BeltLanternSave save = new BeltLanternSave();
        CompoundTag map = nbt.getCompoundOrEmpty(PLAYERS_KEY);
        if (registryLookup != null) {
            var ops = RegistryOps.create(NbtOps.INSTANCE, registryLookup);
            for (String key : map.keySet()) {
                try {
                    UUID uuid = UUID.fromString(key);
                    Tag el = map.get(key);
                    if (el != null && !(el instanceof StringTag)) {
                        ItemStack.CODEC.parse(ops, el).result().ifPresent(stack -> {
                            if (!stack.isEmpty()) {
                                save.playersWithLamps.put(uuid, stack);
                            }
                        });
                    }
                } catch (IllegalArgumentException ignored) {}
            }
        } else {
            return fromNbt(nbt); // fallback to simpler version
        }
        return save;
    }

    public CompoundTag writeNbt(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        CompoundTag map = new CompoundTag();
        if (registryLookup != null) {
            var ops = RegistryOps.create(NbtOps.INSTANCE, registryLookup);
            for (Map.Entry<UUID, ItemStack> e : playersWithLamps.entrySet()) {
                ItemStack stack = e.getValue();
                ItemStack.CODEC.encodeStart(ops, stack).result().ifPresent(el -> map.put(e.getKey().toString(), el));
            }
        } else {
            for (Map.Entry<UUID, ItemStack> e : playersWithLamps.entrySet()) {
                ItemStack stack = e.getValue();
                ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, stack).result().ifPresent(el -> map.put(e.getKey().toString(), el));
            }
        }
        nbt.put(PLAYERS_KEY, map);
        return nbt;
    }
}
