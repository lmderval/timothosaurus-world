package com.torpill.timothosaurus.world;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.blocks.ModBlocks;
import com.torpill.timothosaurus.world.tree.placer.MapleTrunkPlacer;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.foliage.CherryFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.OptionalInt;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE = registerKey("maple");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, MAPLE, Feature.TREE, new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(ModBlocks.MAPLE_LOG),
                        new MapleTrunkPlacer(3, 3, 2),

                        BlockStateProvider.of(ModBlocks.MAPLE_LEAVES),
                        new CherryFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(1), ConstantIntProvider.create(5), 0f, 0f, 0.2f, 0.1f),

                        new ThreeLayersFeatureSize(2, 0, 2, 1, 0, OptionalInt.empty())
                ).build()
        );
    }

    private static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String id) {
        Identifier keyID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, keyID);
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
