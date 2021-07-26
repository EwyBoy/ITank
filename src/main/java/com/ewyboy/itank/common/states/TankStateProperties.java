package com.ewyboy.itank.common.states;

import net.minecraft.world.level.block.state.properties.EnumProperty;

public class TankStateProperties {

    public static final EnumProperty<TankState> TANK_STATE = EnumProperty.create("state", TankState.class);
    public static final EnumProperty<TankColor> TANK_COLOR = EnumProperty.create("color", TankColor.class);

}
