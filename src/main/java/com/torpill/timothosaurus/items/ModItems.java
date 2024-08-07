package com.torpill.timothosaurus.items;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.blocks.ModBlocks;
import com.torpill.timothosaurus.effects.ModEffects;
import com.torpill.timothosaurus.entities.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModItems {
    public static final Item SCHOKO_BONS = register(new SchokoBonsItem(new Item.Settings()), "schoko-bons");
    public static final Item ZAZA_LEAF = register(
            new AliasedBlockItem(ModBlocks.ZAZA_CROP, new Item.Settings()
                    .food(new FoodComponent.Builder()
                            .alwaysEdible()
                            .nutrition(1)
                            .statusEffect(new StatusEffectInstance(ModEffects.ADDICTED, 30 * 20, 0), 1.0f)
                            .build())
            ), "zaza_leaf"
    );
    public static final Item ZAZA_POWDER = register(new Item(new Item.Settings()), "zaza_powder");
    public static final Item MAPLE_SAP = register(new Item(new Item.Settings()), "maple_sap");
    public static final Item MAPLE_SYRUP = register(new Item(new Item.Settings()), "maple_syrup");
    public static final Item TREETAP = register(
            new TreeTapItem(ToolMaterials.WOOD, new Item.Settings()
                    .attributeModifiers(TreeTapItem.createAttributeModifiers(ToolMaterials.WOOD, 0.0F, -2.0F))
            ), "treetap"
    );
    public static final Item TIMOTHOSAURUS_SPAWN_EGG = register(
            new SpawnEggItem(ModEntities.TIMOTHOSAURUS, 0xde2424, 0xf5f5f5, new Item.Settings()), "timothosaurus_spawn_egg"
    );

    public static Item register(Item item, String id) {
        // Create identifier for the item.
        Identifier itemID = Identifier.of(TimothosaurusWorld.MOD_ID, id);

        // Register the item and return the registered item.
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static void initialize() {
        TimothosaurusWorld.LOGGER.info("Initializing Mod Items");

        // Add schoko-bons to the food group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register(itemGroup -> itemGroup.add(SCHOKO_BONS));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                        .register(itemGroup -> {
                            itemGroup.add(ZAZA_LEAF);
                            itemGroup.add(ZAZA_POWDER);
                            itemGroup.add(MAPLE_SAP);
                            itemGroup.add(MAPLE_SYRUP);
                        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                        .register(itemGroup -> itemGroup.add(TREETAP));

        // Eggs to spawn eggs group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS)
                .register(itemGroup -> itemGroup.add(TIMOTHOSAURUS_SPAWN_EGG));
    }
}
