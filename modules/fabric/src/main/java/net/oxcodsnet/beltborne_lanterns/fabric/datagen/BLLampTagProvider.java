package net.oxcodsnet.beltborne_lanterns.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.oxcodsnet.beltborne_lanterns.common.LampRegistry;

import java.util.concurrent.CompletableFuture;

public class BLLampTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public BLLampTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        // valueLookupBuilder exists in 26.1.x, tag() exists in 26.2+
        // Use reflection to support both
        try {
            var method = this.getClass().getSuperclass().getMethod("valueLookupBuilder", net.minecraft.tags.TagKey.class);
            var builder = method.invoke(this, LampRegistry.EXTRA_LAMPS_TAG);
            var addMethod = builder.getClass().getMethod("add", Object[].class);
            addMethod.invoke(builder, new Object[]{new Object[]{Items.LANTERN, Items.SOUL_LANTERN}});
        } catch (Exception e) {
            // 26.2+: use builtInRegistryHolder().key()
            this.tag(LampRegistry.EXTRA_LAMPS_TAG)
                    .add(Items.LANTERN.builtInRegistryHolder().key())
                    .add(Items.SOUL_LANTERN.builtInRegistryHolder().key());
        }
    }
}
