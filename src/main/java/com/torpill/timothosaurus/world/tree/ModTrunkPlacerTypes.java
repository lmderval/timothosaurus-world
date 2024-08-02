package com.torpill.timothosaurus.world.tree;

import com.torpill.timothosaurus.TimothosaurusWorld;
import com.torpill.timothosaurus.mixin.TrunkPlacerTypeInvoker;
import com.torpill.timothosaurus.world.tree.placer.MapleTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerTypes {
    public static final TrunkPlacerType<?> MAPLE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("maple_trunk_placer", MapleTrunkPlacer.CODEC);

    public static void initialize() {
        TimothosaurusWorld.LOGGER.info("Initializing Mod Trunk Placer");
    }
}
