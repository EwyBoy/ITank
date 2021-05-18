package com.ewyboy.itank.common.states;

import net.minecraft.util.IStringSerializable;

public enum TankColor implements IStringSerializable {

    WHITE("white"),
    RED("red"),
    ORANGE("orange"),
    PINK("pink"),
    YELLOW("yellow"),
    LIME("lime"),
    GREEN("green"),
    LIGHT_BLUE("light_blue"),
    CYAN("cyan"),
    BLUE("blue"),
    MAGENTA("magenta"),
    PURPLE("purple"),
    BROWN("brown"),
    GRAY("gray"),
    LIGHT_GRAY("light_gray"),
    BLACK("black");

    private final String color;

    TankColor(String color) {
        this.color = color;
    }

    public String getString() {
        return this.color;
    }

    public String toString() {
        return this.color;
    }

    @Override
    public String getSerializedName() {
        return this.color;
    }
}
