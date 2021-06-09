package com.ewyboy.itank.common.content.tank;

import com.ewyboy.itank.common.states.TankColor;
import com.ewyboy.itank.common.states.TankState;
import com.ewyboy.itank.common.states.TankStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;

public class ColoredTankBlock extends TankBlock {

    public static final EnumProperty<TankColor> COLOR = TankStateProperties.TANK_COLOR;

    public ColoredTankBlock(TankColor color) {
        super();
        setDefaultState(this.getDefaultState().with(STATE, TankState.ONE).with(COLOR, color));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(COLOR).add(STATE);
    }

}
