package com.torpill.timothosaurus;

import com.torpill.timothosaurus.datagen.*;
import com.torpill.timothosaurus.enchantments.ModEnchantments;
import com.torpill.timothosaurus.world.*;
import com.torpill.timothosaurus.world.biome.ModBiomes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class TimothosaurusWorldDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ModWorldGenerator::new);
        pack.addProvider(ModEnchantmentGenerator::new);

        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModEnchantmentTagProvider::new);
        pack.addProvider(ModChestLootTableProvider::new);
        pack.addProvider(ModBiomeTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);

        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.STRUCTURE_SET, ModStructureSets::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.STRUCTURE, ModStructures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.TEMPLATE_POOL, ModTemplatePools::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, ModEnchantments::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
    }
}
