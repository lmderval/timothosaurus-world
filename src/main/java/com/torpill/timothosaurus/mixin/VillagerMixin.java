package com.torpill.timothosaurus.mixin;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.items.ModItems;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public class VillagerMixin {
    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    private void init(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        TimothosaurusWorld.LOGGER.info("{} is interacting with {}.", player, this);
        if (hand != Hand.MAIN_HAND) {
            return;
        }

        Item held = player.getMainHandStack().getItem();
        if (held == ModItems.SCHOKO_BONS) {
            TimothosaurusWorld.LOGGER.info("Trying to give schoko-bons to {}.", this);
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
