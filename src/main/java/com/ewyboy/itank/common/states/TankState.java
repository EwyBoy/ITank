package com.ewyboy.itank.common.states;

import net.minecraft.util.StringRepresentable;

public enum TankState implements StringRepresentable {

    ONE("one"),
    BOT("bot"),
    MID("mid"),
    TOP("top");

    private final String state;

    TankState(String state) {
        this.state = state;
    }

    public String getSerializedName() {
        return this.state;
    }

    public String toString() {
        return this.state;
    }
}
