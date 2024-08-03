package com.torpill.timothosaurus.world;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.blocks.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> TREES_MAPLE = registryKey("trees_maple");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> configuredFeatureEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, TREES_MAPLE, configuredFeatureEntryLookup.getOrThrow(ModConfiguredFeatures.MAPLE),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(2, 0.02f, 1),
                        ModBlocks.MAPLE_SAPLING
                )
        );
    }

    private static RegistryKey<PlacedFeature> registryKey(String id) {
        Identifier keyID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, keyID);
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuredFeature, modifiers));
    }
}
