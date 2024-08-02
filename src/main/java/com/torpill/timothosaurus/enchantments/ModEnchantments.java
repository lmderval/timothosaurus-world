package com.torpill.timothosaurus.enchantments;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.items.ModItems;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> TAP_FORTUNE = registerKey("tap_fortune");

    public static void bootstrap(Registerable<Enchantment> context) {
        register(context, TAP_FORTUNE, Enchantment.builder(
                Enchantment.definition(
                        RegistryEntryList.of(Registries.ITEM.getEntry(ModItems.TREETAP)),
                        2,
                        3,
                        Enchantment.leveledCost(15, 9),
                        Enchantment.leveledCost(65, 9),
                        4,
                        AttributeModifierSlot.MAINHAND
                )
        ));
    }

    private static void register(Registerable<Enchantment> context, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.getValue()));
    }

    private static RegistryKey<Enchantment> registerKey(String id) {
        Identifier keyID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, keyID);
    }
}
