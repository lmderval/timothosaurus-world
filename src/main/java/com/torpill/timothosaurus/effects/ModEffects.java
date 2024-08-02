package com.torpill.timothosaurus.effects;

import com.torpill.timothosaurus.TimothosaurusWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> ADDICTION = register(
            new AddictionStatusEffect(StatusEffectCategory.HARMFUL, 0x24f93a), "addiction"
    );
    public static final RegistryEntry<StatusEffect> ADDICTED = register(
            new AddictedStatusEffect(StatusEffectCategory.NEUTRAL, 0x1ef4e5), "addicted"
    );
    public static final RegistryEntry<StatusEffect> TASTINESS = register(
            new TastinessStatusEffect(StatusEffectCategory.BENEFICIAL, 0xdf531e), "tastiness"
    );

    public static RegistryEntry<StatusEffect> register(StatusEffect effect, String id) {
        Identifier effectID = Identifier.of(TimothosaurusWorld.MOD_ID, id);
        return Registry.registerReference(Registries.STATUS_EFFECT, effectID, effect);
    }

    public static void initialize() {
        TimothosaurusWorld.LOGGER.info("Initializing Mod Effects");
    }
}
