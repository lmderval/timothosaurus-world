package com.torpill.timothosaurus.items;

import com.torpill.timothosaurus.TimothosaurusWorld;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class SchokoBonsItem extends Item {
    public static final FoodComponent SCHOKO_BONS_FOOD_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible()
            .snack()
            .nutrition(1)
            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 5 * 20, 0), 0.1f)
            .build();

    public SchokoBonsItem(Settings settings) {
        super(settings.food(SCHOKO_BONS_FOOD_COMPONENT));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getWorld().isClient()) {
            return ActionResult.PASS;
        }

        if (!(entity instanceof VillagerEntity villager)) {
            return ActionResult.FAIL;
        }
        if (stack.getCount() == 0) {
            return ActionResult.FAIL;
        }

        villager.setExperience(villager.getExperience() + 1);
        stack.decrement(1);

        if (user.getRandom().nextFloat() < 0.05f) {
            villager.dropItem(Items.EMERALD, 1);
        }

        return ActionResult.SUCCESS;
    }
}
