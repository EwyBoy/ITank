package com.ewyboy.itank.common.states;

import net.minecraft.util.StringRepresentable;

public enum TankColor implements StringRepresentable {

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

    public String getSerializedName() {
        return this.color;
    }

    public String toString() {
        return this.color;
    }
}
