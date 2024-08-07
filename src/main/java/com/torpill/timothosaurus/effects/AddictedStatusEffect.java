package com.torpill.timothosaurus.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.Objects;
import java.util.Optional;

public class AddictedStatusEffect extends StatusEffect {
    public AddictedStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(ModEffects.ADDICTION, (90 + amplifier * 30) * 20, getAddictionLevel(entity)));
        return true;
    }

    private int getAddictionLevel(LivingEntity entity) {
        return Optional.ofNullable(entity.getStatusEffect(ModEffects.ADDICTION))
                .map(StatusEffectInstance::getAmplifier)
                .orElse(0);
    }
}
