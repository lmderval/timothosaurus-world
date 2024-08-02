package com.torpill.timothosaurus.entities;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.entities.ai.TimothosaurusAttackGoal;
import com.torpill.timothosaurus.entities.ai.TimothosaurusStealGoal;
import com.torpill.timothosaurus.items.ModItems;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimothosaurusEntity extends PassiveEntity {
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(TimothosaurusEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final AnimationState attackingAnimationState = new AnimationState();
    public int attackingAnimationCooldown = 0;

    private final Map<UUID, ItemStack> stolenItemStacks = new HashMap<>();

    public TimothosaurusEntity(EntityType<? extends TimothosaurusEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(1, new FleeEntityGoal<>(this, HostileEntity.class, 8f, 1.15, 1.45));
        goalSelector.add(1, new FleeEntityGoal<>(this, PlayerEntity.class, 8f, 1.25, 1.45, this::hasStolen));

        goalSelector.add(2, new TemptGoal(this, 1.15, Ingredient.ofItems(ModItems.SCHOKO_BONS), false));

        goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8f));
        goalSelector.add(5, new LookAroundGoal(this));

        goalSelector.add(6, new TimothosaurusStealGoal(this, 1.0));
        goalSelector.add(7, new TimothosaurusAttackGoal(this, 1.0, true));

        targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, false, false, entity -> !hasStolen(entity)));
        targetSelector.add(0, new ActiveTargetGoal<>(this, MerchantEntity.class, 10, false, false, entity -> !hasStolen(entity)));
        targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false, false));
        targetSelector.add(1, new ActiveTargetGoal<>(this, MerchantEntity.class, false, false));
    }

    @Override
    public void setAttacking(boolean attacking) {
        dataTracker.set(ATTACKING, attacking);
    }

    @Override
    public boolean isAttacking() {
        return dataTracker.get(ATTACKING);
    }

    @Override
    public TimothosaurusEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.TIMOTHOSAURUS.create(world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ATTACKING, false);
    }

    public static DefaultAttributeContainer.Builder createTimothosaurusAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1);
    }

    private void updateAnimations() {
        if (isAttacking() && attackingAnimationCooldown <= 0) {
            attackingAnimationCooldown = 20;
            attackingAnimationState.start(age);
        } else {
            --attackingAnimationCooldown;
        }

        if (!isAttacking()) {
            attackingAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (getWorld().isClient()) {
            updateAnimations();
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean damaged = super.damage(source, amount);
        if (damaged && source.getAttacker() instanceof LivingEntity attacker && hasStolen(attacker)) {
            UUID uuid = attacker.getUuid();
            ItemStack stolenItemStack = stolenItemStacks.get(uuid);
            dropStack(stolenItemStack);
            stolenItemStacks.remove(uuid);
        }
        return damaged;
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();

        for (ItemStack itemStack : stolenItemStacks.values()) {
            dropStack(itemStack);
        }

        stolenItemStacks.clear();
    }

    private @Nullable Hand getHandToSteal(LivingEntity entity) {
        if (entity.getMainHandStack() != ItemStack.EMPTY) {
            return Hand.MAIN_HAND;
        }
        if (entity.getOffHandStack() != ItemStack.EMPTY) {
            return Hand.OFF_HAND;
        }

        return null;
    }

    public boolean trySteal(LivingEntity target) {
        if (getWorld().isClient()) {
            return false;
        }

        if (!canSteal(target)) {
            return false;
        }

        Hand hand = getHandToSteal(target);
        ItemStack stack = target.getStackInHand(hand);
        stolenItemStacks.put(target.getUuid(), stack);
        target.setStackInHand(hand, ItemStack.EMPTY);

        tryAttack(target);

        return true;
    }

    public boolean canSteal(LivingEntity target) {
        return !hasStolen(target) && getHandToSteal(target) != null;
    }

    public boolean hasStolen(LivingEntity target) {
        return stolenItemStacks.containsKey(target.getUuid());
    }

    public ItemStack getStolenItemStack(LivingEntity target) {
        if (hasStolen(target)) {
            return stolenItemStacks.get(target.getUuid());
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (!stolenItemStacks.isEmpty()) {
            NbtList nbtStolenItemStacks = new NbtList();
            for (UUID uuid : stolenItemStacks.keySet()) {
                NbtCompound nbtStolenItemStack = new NbtCompound();
                nbtStolenItemStack.putUuid("UUID", uuid);
                nbtStolenItemStack.put("ItemStack", stolenItemStacks.get(uuid).encode(getRegistryManager()));
                nbtStolenItemStacks.add(nbtStolenItemStack);
            }
            nbt.put("StolenItemStacks", nbtStolenItemStacks);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        stolenItemStacks.clear();
        if (nbt.contains("StolenItemStacks")) {
            NbtList nbtStolenItemStacks = nbt.getList("StolenItemStacks", NbtList.COMPOUND_TYPE);
            for (int i = 0; i < nbtStolenItemStacks.size(); i++) {
                NbtCompound nbtStolenItemStack = nbtStolenItemStacks.getCompound(i);
                UUID uuid = nbtStolenItemStack.getUuid("UUID");
                ItemStack stolenItemStack = ItemStack.fromNbtOrEmpty(getRegistryManager(), nbtStolenItemStack.getCompound("ItemStack"));
                if (!stolenItemStack.isEmpty()) {
                    stolenItemStacks.put(uuid, stolenItemStack);
                }
            }
        }
    }
}
