package com.ewyboy.itank.common.states;

import net.minecraft.util.IStringSerializable;

public enum TankState implements IStringSerializable {

    ONE("one"),
    BOT("bot"),
    MID("mid"),
    TOP("top");

    private final String state;

    TankState(String state) {
        this.state = state;
    }

    public String getString() {
        return this.state;
    }

    public String toString() {
        return this.state;
    }

    @Override
    public String getSerializedName() {
        return this.state;
    }
}
