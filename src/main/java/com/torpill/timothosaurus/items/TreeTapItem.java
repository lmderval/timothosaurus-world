package com.torpill.timothosaurus.items;

import com.torpill.timothosaurus.blocks.MapleLogBlock;
import com.torpill.timothosaurus.blocks.ModBlocks;
import com.torpill.timothosaurus.enchantments.ModEnchantmentHelper;
import com.torpill.timothosaurus.enchantments.ModEnchantments;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class TreeTapItem extends ToolItem {
    private static final Map<Block, Item> SAP_ITEMS = Map.of(
            ModBlocks.MAPLE_LOG, ModItems.MAPLE_SAP
    );

    public TreeTapItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        if (shouldCancelTapAttempt(context)) {
            return ActionResult.PASS;
        } else {
            Optional<Item> optional = tryTap(world, blockPos, playerEntity, world.getBlockState(blockPos));
            if (optional.isEmpty()) {
                return ActionResult.PASS;
            } else {
                ItemStack itemStack = context.getStack();
                if (playerEntity instanceof ServerPlayerEntity serverPlayerEntity) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayerEntity, blockPos, itemStack);
                }

                BlockPos dropPos = blockPos.offset(context.getSide());
                ItemEntity itemEntity = new ItemEntity(world, dropPos.getX(), dropPos.getY(), dropPos.getZ(), new ItemStack(optional.get(), sapDropCount(world, itemStack)));
                world.spawnEntity(itemEntity);
                if (playerEntity != null) {
                    itemStack.damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                }

                return ActionResult.success(world.isClient());
            }
        }
    }

    private static boolean shouldCancelTapAttempt(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        if (playerEntity == null) {
            return true;
        }
        return context.getHand().equals(Hand.MAIN_HAND) && playerEntity.getOffHandStack().isOf(Items.SHIELD) && !playerEntity.shouldCancelInteraction();
    }

    private Optional<Item> tryTap(World world, BlockPos blockPos, @Nullable PlayerEntity playerEntity, BlockState blockState) {
        Optional<Item> optional = getSapItem(blockState);
        if (optional.isPresent()) {
            BlockState sapRemoved = blockState.with(MapleLogBlock.SAP, false);
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HONEYCOMB_WAX_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.setBlockState(blockPos, sapRemoved, 11);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, sapRemoved));
        }
        return optional;
    }

    private Optional<Item> getSapItem(BlockState blockState) {
        if (!SAP_ITEMS.containsKey(blockState.getBlock()) || !blockState.get(MapleLogBlock.SAP)) {
            return Optional.empty();
        }
        return Optional.ofNullable(SAP_ITEMS.get(blockState.getBlock()));
    }

    private int sapDropCount(World world, ItemStack stack) {
        int tapFortuneLevel = ModEnchantmentHelper.getLevel(world, stack, ModEnchantments.TAP_FORTUNE);
        return 1 + world.getRandom().nextBetween(MathHelper.floorDiv(tapFortuneLevel, 3), MathHelper.floorDiv(2 * (tapFortuneLevel + 1), 3) + 1);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, (baseAttackDamage + material.getAttackDamage()), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }
}
