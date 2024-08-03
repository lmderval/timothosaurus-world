package com.torpill.timothosaurus.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

public class ModOverworldRegion extends Region {
    public ModOverworldRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        super.addBiomes(registry, mapper);

        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.COOL)
                .humidity(ParameterUtils.Humidity.span(ParameterUtils.Humidity.DRY, ParameterUtils.Humidity.NEUTRAL))
                .continentalness(ParameterUtils.Continentalness.FAR_INLAND)
                .erosion(ParameterUtils.Erosion.span(ParameterUtils.Erosion.EROSION_0, ParameterUtils.Erosion.EROSION_2))
                .depth(ParameterUtils.Depth.span(ParameterUtils.Depth.SURFACE, ParameterUtils.Depth.FLOOR))
                .weirdness(ParameterUtils.Weirdness.VALLEY)
                .build()
                .forEach(point -> builder.add(point, ModBiomes.MAPLE_FOREST));

        // Add our points to the mapper
        builder.build().forEach(mapper);
    }
}
