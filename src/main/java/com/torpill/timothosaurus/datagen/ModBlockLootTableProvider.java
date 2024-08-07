package com.torpill.timothosaurus.datagen;

import com.torpill.timothosaurus.blocks.ModBlocks;
import com.torpill.timothosaurus.blocks.ZazaCropBlock;
import com.torpill.timothosaurus.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.MAPLE_LOG);
        addDrop(ModBlocks.MAPLE_WOOD);
        addDrop(ModBlocks.STRIPPED_MAPLE_LOG);
        addDrop(ModBlocks.STRIPPED_MAPLE_WOOD);
        addDrop(ModBlocks.MAPLE_PLANKS);
        addDrop(ModBlocks.MAPLE_LEAVES, leavesDrops(ModBlocks.MAPLE_LEAVES, ModBlocks.MAPLE_SAPLING, 0.1f));
        addDrop(ModBlocks.MAPLE_SAPLING);

        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition.builder(ModBlocks.ZAZA_CROP)
                .properties(StatePredicate.Builder.create()
                        .exactMatch(ZazaCropBlock.AGE, 5)
                );
        addDrop(ModBlocks.ZAZA_CROP, cropDrops(ModBlocks.ZAZA_CROP, ModItems.ZAZA_LEAF, ModItems.ZAZA_LEAF, builder));
    }
}
