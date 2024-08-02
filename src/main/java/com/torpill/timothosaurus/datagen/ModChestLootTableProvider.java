package com.torpill.timothosaurus.datagen;

import com.torpill.timothosaurus.items.ModItems;
import com.torpill.timothosaurus.loot_tables.ModLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModChestLootTableProvider extends SimpleFabricLootTableProvider {
    public ModChestLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.CHEST);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        lootTableBiConsumer.accept(ModLootTables.ZAZA_HOUSE_ATTIC, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(BinomialLootNumberProvider.create(10, 0.75f))
                        .with(ItemEntry.builder(ModItems.ZAZA_LEAF)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)))
                        )
                        .with(ItemEntry.builder(ModItems.ZAZA_POWDER)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)))
                        )
                        .with(ItemEntry.builder(Items.SUGAR)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)))
                        )
                )
        );
        lootTableBiConsumer.accept(ModLootTables.ZAZA_HOUSE_ROOM, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(3.0f, 7.0f))
                        .with(ItemEntry.builder(ModItems.SCHOKO_BONS)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 6.0f)))
                        )
                        .with(ItemEntry.builder(ModItems.MAPLE_SAP)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)))
                        )
                        .with(ItemEntry.builder(ModItems.MAPLE_SYRUP)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)))
                        )
                )
                .pool(LootPool.builder()
                        .rolls(BinomialLootNumberProvider.create(2, 0.1f))
                        .with(ItemEntry.builder(Items.MUSIC_DISC_MELLOHI)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))
                        )
                        .with(ItemEntry.builder(Items.MUSIC_DISC_WAIT)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))
                        )
                        .with(ItemEntry.builder(Items.MUSIC_DISC_WARD)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))
                        )
                )
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .with(ItemEntry.builder(Items.MILK_BUCKET)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))
                        )
                )
                .pool(LootPool.builder()
                        .rolls(BinomialLootNumberProvider.create(4, 0.5f))
                        .with(ItemEntry.builder(Items.POTATO)
                                .apply(SetCountLootFunction.builder(BinomialLootNumberProvider.create(6, 0.35f)))
                        )
                )
                .pool(LootPool.builder()
                        .rolls(BinomialLootNumberProvider.create(2, 0.2f))
                        .with(ItemEntry.builder(Items.EMERALD)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)))
                        )
                )
        );
    }
}
