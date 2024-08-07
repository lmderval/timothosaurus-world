package com.torpill.timothosaurus.world.biome;

import com.torpill.timothosaurus.entities.ModEntities;
import com.torpill.timothosaurus.world.ModPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

public class MapleForestBiome {
    public static Biome create(Registerable<Biome> context) {
        RegistryEntryLookup<PlacedFeature> placedFeatureEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> configuredCarverEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);

        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.TIMOTHOSAURUS, 2, 2, 3));
        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 10, 4, 6));
        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 3, 5));

        DefaultBiomeFeatures.addBatsAndMonsters(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(placedFeatureEntryLookup, configuredCarverEntryLookup);
        BiomeGenerationHelper.overworld(biomeBuilder);

        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addExtraGoldOre(biomeBuilder);

        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.TREES_MAPLE);
        DefaultBiomeFeatures.addForestFlowers(biomeBuilder);
        DefaultBiomeFeatures.addForestGrass(biomeBuilder);
        DefaultBiomeFeatures.addPlainsTallGrass(biomeBuilder);

        float temperature = 0.3f;

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.2f)
                .temperature(temperature)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects(new BiomeEffects.Builder()
                        .fogColor(BiomeGenerationHelper.DEFAULT_FOG_COLOR)
                        .waterColor(BiomeGenerationHelper.DEFAULT_WATER_COLOR)
                        .waterFogColor(BiomeGenerationHelper.DEFAULT_WATER_FOG_COLOR)
                        .skyColor(OverworldBiomeCreator.getSkyColor(temperature))
                        .moodSound(BiomeMoodSound.CAVE)
                        .build()
                )
                .build();
    }
}
