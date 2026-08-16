package net.oxcodsnet.beltborne_lanterns.common.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

/**
 * Simple cube model for lantern.
 * Uses simple UV mapping that should work with any texture.
 */
public class LanternBeltModel extends EntityModel<EntityRenderState> {
    private static final String LANTERN = "lantern";

    public LanternBeltModel(ModelPart root) {
        super(root);
    }

    /**
     * Creates a simple cube lantern model.
     * Using standard cube UV layout (like Minecraft blocks).
     */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        
        // Simple 6x8x6 box (lantern size)
        // Using standard cube UV mapping
        partdefinition.addOrReplaceChild(
            LANTERN, 
            CubeListBuilder.create()
                .texOffs(0, 0) // UV starts at 0,0
                .addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), // 6x8x6 lantern
            PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        
        // Texture size 16x48 (but we'll use it as wrapped cube)
        return LayerDefinition.create(meshdefinition, 16, 48);
    }
}
