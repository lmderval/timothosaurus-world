package com.torpill.timothosaurus.world.tree.placer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.blocks.MapleLogBlock;
import com.torpill.timothosaurus.world.tree.ModTrunkPlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MapleTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<MapleTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            fillTrunkPlacerFields(instance).apply(instance, MapleTrunkPlacer::new)
    );

    public MapleTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacerTypes.MAPLE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        setToDirt(world, replacer, random, startPos.down(), config);
        int levels = random.nextInt(firstRandomHeight) + 1;
        int treeHeight = baseHeight * levels + random.nextInt(secondRandomHeight + 1) + 2;

        for (int i = 0; i < treeHeight; i++) {
            BlockState blockState = config.trunkProvider.get(random, startPos.up(i));
            if (i != 1 || !blockState.contains(MapleLogBlock.SAP) || random.nextFloat() < 0.8) {
                getAndSetState(world, replacer, random, startPos.up(i), config);
            } else {
                replacer.accept(startPos.up(i), (BlockState) Function.identity().apply(blockState.with(MapleLogBlock.SAP, true)));
            }
        }

        List<FoliagePlacer.TreeNode> nodes = new ArrayList<>();
        nodes.add(new FoliagePlacer.TreeNode(startPos.up(treeHeight), 0, false));

        List<Pair<Direction, Direction.Axis>> axes = List.of(
                new Pair<>(Direction.NORTH, Direction.Axis.Z),
                new Pair<>(Direction.SOUTH, Direction.Axis.Z),
                new Pair<>(Direction.EAST, Direction.Axis.X),
                new Pair<>(Direction.WEST, Direction.Axis.X)
        );

        axes.forEach(ax -> {
            Direction direction = ax.getLeft();
            Direction.Axis axis = ax.getRight();
            for (int i = 0; i < levels; i++) {
                int w = levels - i + 1;
                int j = 3 * i + 2 + random.nextInt(2);
                for (int k = 1; k < w; k++) {
                    replacer.accept(startPos.up(j).offset(direction, k), (BlockState) Function.identity().apply(config.trunkProvider.get(random, startPos.up(j).offset(direction, k)).with(PillarBlock.AXIS, axis)));
                }
                replacer.accept(startPos.up(j + 1).offset(direction, w), (BlockState) Function.identity().apply(config.trunkProvider.get(random, startPos.up(j + 1).offset(direction, w)).with(PillarBlock.AXIS, axis)));
                nodes.add(new FoliagePlacer.TreeNode(startPos.up(j + 1).offset(direction, w), (treeHeight - j) / 7, false));
            }
        });

        return nodes;
    }
}
