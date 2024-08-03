package com.torpill.timothosaurus;

import com.torpill.timothosaurus.blocks.ModBlocks;
import com.torpill.timothosaurus.effects.ModEffects;
import com.torpill.timothosaurus.entities.ModEntities;
import com.torpill.timothosaurus.items.ModItems;
import com.torpill.timothosaurus.loot_tables.ModLootTables;
import com.torpill.timothosaurus.potions.ModPotions;
import com.torpill.timothosaurus.tags.ModTags;
import com.torpill.timothosaurus.world.tree.ModTrunkPlacerTypes;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimothosaurusWorld implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "timothosaurus-world";
    public static final Logger LOGGER = LoggerFactory.getLogger("Thimothosaurus");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing Timothosaurus World");

		ModTags.initialize();
		ModEffects.initialize();
		ModItems.initialize();
		ModBlocks.initialize();
		ModPotions.initialize();
		ModEntities.initialize();
		ModTrunkPlacerTypes.initialize();
		ModLootTables.initialize();
	}
}