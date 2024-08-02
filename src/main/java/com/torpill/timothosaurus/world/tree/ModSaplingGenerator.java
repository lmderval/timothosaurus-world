package com.torpill.timothosaurus.world.tree;

import com.torpill.timothosaurus.world.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModSaplingGenerator {
    public static final SaplingGenerator MAPLE = new SaplingGenerator("maple", Optional.empty(), Optional.of(ModConfiguredFeatures.MAPLE), Optional.empty());
}
