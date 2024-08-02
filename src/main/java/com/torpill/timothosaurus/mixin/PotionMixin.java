package com.torpill.timothosaurus.mixin;

import com.torpill.timothosaurus.potions.IPotionMixin;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Potion.class)
public class PotionMixin implements IPotionMixin {
    @Shadow @Final @Nullable private String baseName;

    @Override
    public @Nullable String getBaseName() {
        return this.baseName;
    }
}
