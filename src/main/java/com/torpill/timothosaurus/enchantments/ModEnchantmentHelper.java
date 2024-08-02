package com.torpill.timothosaurus.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class ModEnchantmentHelper {
    public static int getLevel(World world, ItemStack stack, RegistryKey<Enchantment> enchantment) {
        DynamicRegistryManager registryManager = world.getRegistryManager();
        Registry<Enchantment> enchantmentRegistry = registryManager.get(RegistryKeys.ENCHANTMENT);
        RegistryEntry<Enchantment> enchantmentRegistryEntry = enchantmentRegistry.getEntry(enchantmentRegistry.get(enchantment));
        return stack.getEnchantments().getLevel(enchantmentRegistryEntry);
    }
}
