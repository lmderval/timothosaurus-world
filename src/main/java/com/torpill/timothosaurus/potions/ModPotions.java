package com.torpill.timothosaurus.potions;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.effects.ModEffects;
import com.torpill.timothosaurus.items.ModItems;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModPotions {
    public static final RegistryEntry<Potion> ADDICTION = register(
            new Potion(new StatusEffectInstance(ModEffects.ADDICTION, 9600)),
            "addiction"
    );
    public static final RegistryEntry<Potion> LONG_ADDICTION = register(
            new Potion("addiction", new StatusEffectInstance(ModEffects.ADDICTION, 24000)),
            "long_addiction"
    );
    public static final RegistryEntry<Potion> STRONG_ADDICTION = register(
            new Potion("addiction", new StatusEffectInstance(ModEffects.ADDICTION, 3600, 1)),
            "strong_addiction"
    );

    public static final Map<RegistryEntry<Potion>, RegistryEntry<Potion>> TASTY_POTIONS = Registries.POTION.streamEntries()
            .map(entry -> {
                Potion potion = entry.value();
                String id = entry.getIdAsString().split(":")[1];
                String baseName = ((IPotionMixin) potion).getBaseName();
                List<StatusEffectInstance> effects = new ArrayList<>(potion.getEffects());
                effects.add(new StatusEffectInstance(ModEffects.TASTINESS, 600));
                StatusEffectInstance[] effectInstances = new StatusEffectInstance[effects.size()];
                effects.toArray(effectInstances);
                return new Pair<>(entry, register(new Potion(baseName != null ? baseName : id, effectInstances), "tasty_" + id));
            })
            .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

    private static RegistryEntry<Potion> register(Potion potion, String id) {
        Identifier potionID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return Registry.registerReference(Registries.POTION, potionID, potion);
    }

    public static void initialize() {
        TimothosaurusWorld.LOGGER.info("Initializing Mod Potions");

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.AWKWARD, ModItems.ZAZA_POWDER, ADDICTION);
            builder.registerPotionRecipe(ADDICTION, Items.REDSTONE, LONG_ADDICTION);
            builder.registerPotionRecipe(ADDICTION, Items.GLOWSTONE_DUST, STRONG_ADDICTION);

            TASTY_POTIONS.forEach((from, to) -> builder.registerPotionRecipe(from, ModItems.MAPLE_SYRUP, to));
        });
    }
}
