package com.torpill.timothosaurus.entities.ai;

import com.torpill.timothosaurus.entities.TimothosaurusEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.util.Hand;

public class TimothosaurusAttackGoal extends MeleeAttackGoal {
    private final TimothosaurusEntity entity;
    private int attackDelay = 5;
    private int ticksUntilNextAttack = 15;
    private boolean shouldContinueTillNextAttack = false;

    public TimothosaurusAttackGoal(TimothosaurusEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = mob;
    }

    protected void resetAttackCooldown() {
        ticksUntilNextAttack = 20;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return ticksUntilNextAttack <= attackDelay;
    }

    protected boolean isTimeToAttack() {
        return ticksUntilNextAttack <= 0;
    }

    protected void performAttack(LivingEntity target) {
        resetAttackCooldown();
        entity.swingHand(Hand.MAIN_HAND);
        entity.tryAttack(target);
    }

    @Override
    public void start() {
        super.start();

        attackDelay = 5;
        ticksUntilNextAttack = 15;
    }

    @Override
    protected void attack(LivingEntity target) {
        if (canAttack(target)) {
            shouldContinueTillNextAttack = true;

            if (isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }

            if (isTimeToAttack()) {
                entity.getLookControl().lookAt(target);
                performAttack(target);
            }
        } else {
            resetAttackCooldown();

            shouldContinueTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackingAnimationCooldown = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (shouldContinueTillNextAttack) {
            ticksUntilNextAttack = Math.max(ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        super.stop();

        entity.setAttacking(false);
    }
}
