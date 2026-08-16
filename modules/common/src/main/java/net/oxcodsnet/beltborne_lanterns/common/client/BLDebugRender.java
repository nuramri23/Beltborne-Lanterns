package net.oxcodsnet.beltborne_lanterns.common.client;

import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Client-side common debug helpers (platform-agnostic).
 */
public final class BLDebugRender {
    private BLDebugRender() {}

    /**
     * Draws axes gizmo and a small cube at origin.
     * No-op stub - MultiBufferSource was removed in MC 26.2+
     */
    public static void drawAxesAndAnchor(PoseStack matrices, Object vertices, float axisLength) {
        // Intentionally left blank on 26.x to avoid API churn.
    }
}
