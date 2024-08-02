package com.torpill.timothosaurus.entities.ai;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.entities.TimothosaurusEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class TimothosaurusStealGoal extends Goal {
    private final TimothosaurusEntity entity;
    private final double speed;

    private Path path;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int updateCountdownTicks;
    private int cooldown;
    private long lastUpdateTime;

    private int stealDelay = 5;

    private static final int STEAL_INTERVAL_TICKS = 20;
    private static final long MAX_STEAL_TIME = 20L;

    public TimothosaurusStealGoal(TimothosaurusEntity entity, double speed) {
        this.entity = entity;
        this.speed = speed;
        setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        long time = entity.getWorld().getTime();
        if (time - lastUpdateTime < MAX_STEAL_TIME) {
            return false;
        } else {
            lastUpdateTime = time;
            LivingEntity target = entity.getTarget();
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else if (!entity.canSteal(target)) {
                return false;
            } else {
                path = entity.getNavigation().findPathTo(target, 0);
                if (path != null) {
                    return true;
                } else {
                    return entity.isInAttackRange(target);
                }
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity target = entity.getTarget();
        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!entity.canSteal(target)) {
            return false;
        } else if (!entity.isInWalkTargetRange(target.getBlockPos())) {
            return false;
        } else {
            return EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target);
        }
    }

    @Override
    public void start() {
        entity.getNavigation().startMovingAlong(path, speed);
        entity.setAttacking(true);
        updateCountdownTicks = 0;
        cooldown = 0;

        stealDelay = 5;
    }

    @Override
    public void stop() {
        LivingEntity target = entity.getTarget();
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) {
            entity.setTarget(null);
        }

        entity.setAttacking(false);
        entity.getNavigation().stop();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.getLookControl().lookAt(target, 30.0F, 30.0F);
            updateCountdownTicks = Math.max(updateCountdownTicks - 1, 0);

            if (entity.getVisibilityCache().canSee(target) && updateCountdownTicks <= 0 && (
                    targetX == 0.0 && targetY == 0.0 && targetZ == 0.0
                            || target.squaredDistanceTo(targetX, targetY, targetZ) >= 1.0
                            || entity.getRandom().nextFloat() < 0.05F
            )) {
                targetX = target.getX();
                targetY = target.getY();
                targetZ = target.getZ();
                updateCountdownTicks = 4 + entity.getRandom().nextInt(7);

                double d = entity.squaredDistanceTo(target);
                if (d > 1024.0) {
                    updateCountdownTicks += 10;
                } else if (d > 256.0) {
                    updateCountdownTicks += 5;
                }

                if (!entity.getNavigation().startMovingTo(target, speed)) {
                    updateCountdownTicks += 15;
                }

                updateCountdownTicks = getTickCount(updateCountdownTicks);
            }

            cooldown = Math.max(cooldown - 1, 0);
            steal(target);
        }
    }

    protected boolean canSteal(LivingEntity target) {
        return entity.canSteal(target) && entity.isInAttackRange(target) && entity.getVisibilityCache().canSee(target);
    }

    protected void performSteal(LivingEntity target) {
        resetCooldown();

        entity.swingHand(Hand.MAIN_HAND);
        entity.trySteal(target);
    }

    protected void steal(LivingEntity target) {
        TimothosaurusWorld.LOGGER.info("Going to steal {}...", target);
        if (canSteal(target)) {
            TimothosaurusWorld.LOGGER.warn("Trying to steal {}...", target);
            if (isTimeToStartAnimation()) {
                entity.setAttacking(true);
            }

            if (isCooledDown()) {
                entity.getLookControl().lookAt(target);
                performSteal(target);
            }
        } else {
            resetCooldown();

            entity.setAttacking(false);
            entity.attackingAnimationCooldown = 0;
        }
    }

    protected void resetCooldown() {
        cooldown = getTickCount(STEAL_INTERVAL_TICKS);
    }

    protected boolean isCooledDown() {
        return cooldown <= 0;
    }

    protected boolean isTimeToStartAnimation() {
        return cooldown <= stealDelay;
    }
}
