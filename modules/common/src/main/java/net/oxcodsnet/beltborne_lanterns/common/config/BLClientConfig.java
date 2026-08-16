package net.oxcodsnet.beltborne_lanterns.common.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.oxcodsnet.beltborne_lanterns.BLMod;

@Config(name = BLMod.MOD_ID)
public class BLClientConfig implements ConfigData {
    public boolean debug = false;

    // Values stored in thousandths (1/1000)
    // Default values adjusted for optimal belt positioning
    @ConfigEntry.BoundedDiscrete(min = -2000, max = 2000)
    public int offsetX100 = -427; // -0.427 blocks (left side)

    @ConfigEntry.BoundedDiscrete(min = -2000, max = 2000)
    public int offsetY100 = -50; // -0.050 blocks (lower)

    @ConfigEntry.BoundedDiscrete(min = -2000, max = 2000)
    public int offsetZ100 = -210; // -0.210 blocks (forward)

    @ConfigEntry.BoundedDiscrete(min = -2000, max = 2000)
    public int pivotX100 = 500; // 0.5 blocks

    @ConfigEntry.BoundedDiscrete(min = -2000, max = 2000)
    public int pivotY100 = 600; // 0.6 blocks

    @ConfigEntry.BoundedDiscrete(min = -2000, max = 2000)
    public int pivotZ100 = 500; // 0.5 blocks

    @ConfigEntry.BoundedDiscrete(min = -360, max = 360)
    public int rotXDeg = 180; // flipped

    @ConfigEntry.BoundedDiscrete(min = -360, max = 360)
    public int rotYDeg = 0;

    @ConfigEntry.BoundedDiscrete(min = -360, max = 360)
    public int rotZDeg = 0;

    @ConfigEntry.BoundedDiscrete(min = 250, max = 1000)
    public int scale100 = 500; // 0.5 scale (50% size)


    @ConfigEntry.Category("lamps")
    public java.util.List<ExtraLampEntry> extraLampLight = new java.util.ArrayList<>();

    public float fOffsetX() { return offsetX100 / 1000f; }
    public float fOffsetY() { return offsetY100 / 1000f; }
    public float fOffsetZ() { return offsetZ100 / 1000f; }
    public float fPivotX()  { return pivotX100 / 1000f; }
    public float fPivotY()  { return pivotY100 / 1000f; }
    public float fPivotZ()  { return pivotZ100 / 1000f; }
    public float fScale()   { return scale100 / 1000f; }

    public static class ExtraLampEntry {
        public String id = "modid:item_id";

        @ConfigEntry.BoundedDiscrete(min = 0, max = 15)
        public int luminance = 15;

        public ExtraLampEntry() {}

        public ExtraLampEntry(String id, int luminance) {
            this.id = id;
            this.luminance = luminance;
        }
    }
}
