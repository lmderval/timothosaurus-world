package com.torpill.timothosaurus.blocks;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.world.tree.ModSaplingGenerator;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block MAPLE_LOG = register(new MapleLogBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)), "maple_log", true);
    public static final Block MAPLE_WOOD = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)), "maple_wood", true);
    public static final Block STRIPPED_MAPLE_LOG = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)), "stripped_maple_log", true);
    public static final Block STRIPPED_MAPLE_WOOD = register(new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)), "stripped_maple_wood", true);

    public static final Block MAPLE_PLANKS = register(new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)), "maple_planks", true);
    public static final Block MAPLE_LEAVES = register(new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)), "maple_leaves", true);

    public static final Block MAPLE_SAPLING = register(new SaplingBlock(ModSaplingGenerator.MAPLE, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)), "maple_sapling", true);

    public static final Block ZAZA_CROP = register(new ZazaCropBlock(AbstractBlock.Settings.copy(Blocks.CARROTS)), "zaza", false);

    public static Block register(Block block, String id, boolean registerItem) {
        Identifier blockID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        if (registerItem) {
            BlockItem item = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, blockID, item);
        }
        return Registry.register(Registries.BLOCK, blockID, block);
    }

    public static void initialize() {
        TimothosaurusWorld.LOGGER.info("Initializing Mod Blocks");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
                .register(itemGroup -> {
                    itemGroup.add(MAPLE_LOG);
                    itemGroup.add(MAPLE_WOOD);
                    itemGroup.add(STRIPPED_MAPLE_LOG);
                    itemGroup.add(STRIPPED_MAPLE_WOOD);
                    itemGroup.add(MAPLE_PLANKS);
                });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
                .register(itemGroup -> {
                    itemGroup.add(MAPLE_LOG);
                    itemGroup.add(MAPLE_LEAVES);
                    itemGroup.add(MAPLE_SAPLING);
                });

        // Strippable
        StrippableBlockRegistry.register(ModBlocks.MAPLE_LOG, ModBlocks.STRIPPED_MAPLE_LOG);
        StrippableBlockRegistry.register(ModBlocks.MAPLE_WOOD, ModBlocks.STRIPPED_MAPLE_WOOD);

        // Flammable Blocks
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAPLE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAPLE_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_MAPLE_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_MAPLE_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAPLE_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAPLE_LEAVES, 30, 60);
    }
}
