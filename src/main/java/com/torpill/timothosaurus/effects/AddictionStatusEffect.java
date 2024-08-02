package com.torpill.timothosaurus.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class AddictionStatusEffect extends StatusEffect {
    public AddictionStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!isAddicted(entity)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10 * 20, amplifier));
        }
        return true;
    }

    private boolean isAddicted(LivingEntity entity) {
        return entity.hasStatusEffect(ModEffects.ADDICTED);
    }
}
