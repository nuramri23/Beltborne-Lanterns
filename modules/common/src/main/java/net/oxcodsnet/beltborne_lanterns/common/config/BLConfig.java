package net.oxcodsnet.beltborne_lanterns.common.config;

/**
 * Platform-agnostic config snapshot for rendering and pose.
 * Values mirror the fabric UI config but live in common.
 * Values are in thousandths (1/1000) for precision.
 * 
 * Default values match BLClientConfig defaults.
 */
public final class BLConfig {
    // Offset from body center (in blocks, stored as 1/1000)
    public int offsetX100 = -427;   // -0.427 blocks (left side)
    public int offsetY100 = -50;    // -0.050 blocks (lower)
    public int offsetZ100 = -210;   // -0.210 blocks (forward)

    // Pivot point for rotation (in blocks, stored as 1/1000)
    public int pivotX100 = 500;     // 0.5 blocks
    public int pivotY100 = 600;     // 0.6 blocks
    public int pivotZ100 = 500;     // 0.5 blocks

    // Rotation in degrees
    public int rotXDeg = 180;       // flipped
    public int rotYDeg = 0;
    public int rotZDeg = 0;

    // Scale (stored as 1/1000)
    public int scale100 = 500;      // 0.5 scale (50% size)

    public float fOffsetX() { return offsetX100 / 1000f; }
    public float fOffsetY() { return offsetY100 / 1000f; }
    public float fOffsetZ() { return offsetZ100 / 1000f; }
    public float fPivotX()  { return pivotX100 / 1000f; }
    public float fPivotY()  { return pivotY100 / 1000f; }
    public float fPivotZ()  { return pivotZ100 / 1000f; }
    public float fScale()   { return scale100 / 1000f; }
}

