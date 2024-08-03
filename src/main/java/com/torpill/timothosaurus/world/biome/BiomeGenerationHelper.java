package com.torpill.timothosaurus.world.biome;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class BiomeGenerationHelper {
    public static final int DEFAULT_WATER_COLOR = 0x3f76e4;
    public static final int DEFAULT_WATER_FOG_COLOR = 0x050533;
    public static final int DEFAULT_FOG_COLOR = 0xc0d8ff;

    public static void overworld(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }
}
