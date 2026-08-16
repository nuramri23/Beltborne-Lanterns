package net.oxcodsnet.beltborne_lanterns.common.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

/**
 * Registry for custom model layers used by Beltborne Lanterns.
 */
public class BLModelLayers {
    public static final ModelLayerLocation LANTERN_BELT_LAYER = new ModelLayerLocation(
        Identifier.fromNamespaceAndPath("beltborne_lanterns", "lantern_belt"),
        "main"
    );
}
