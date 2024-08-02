package com.torpill.timothosaurus.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class MapleLogBlock extends PillarBlock {
    public static final BooleanProperty SAP = BooleanProperty.of("sap");

    public MapleLogBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(SAP, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SAP);
    }
}
