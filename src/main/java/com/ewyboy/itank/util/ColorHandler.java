package com.ewyboy.itank.util;

import com.ewyboy.itank.common.states.TankColor;
import com.ewyboy.itank.config.ConfigOptions;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.awt.*;
import java.util.HashMap;

public class ColorHandler {

    private static final HashMap<TankColor, Integer> stateColorToIntegerColorMap = new HashMap<>();

    static {
        stateColorToIntegerColorMap.put(TankColor.WHITE, ConfigOptions.Colors.tankColorWhite);
        stateColorToIntegerColorMap.put(TankColor.RED, ConfigOptions.Colors.tankColorRed);
        stateColorToIntegerColorMap.put(TankColor.ORANGE, ConfigOptions.Colors.tankColorOrange);
        stateColorToIntegerColorMap.put(TankColor.PINK, ConfigOptions.Colors.tankColorPink);
        stateColorToIntegerColorMap.put(TankColor.YELLOW, ConfigOptions.Colors.tankColorYellow);
        stateColorToIntegerColorMap.put(TankColor.LIME, ConfigOptions.Colors.tankColorLime);
        stateColorToIntegerColorMap.put(TankColor.GREEN, ConfigOptions.Colors.tankColorGreen);
        stateColorToIntegerColorMap.put(TankColor.LIGHT_BLUE, ConfigOptions.Colors.tankColorLightBlue);
        stateColorToIntegerColorMap.put(TankColor.CYAN, ConfigOptions.Colors.tankColorCyan);
        stateColorToIntegerColorMap.put(TankColor.BLUE, ConfigOptions.Colors.tankColorBlue);
        stateColorToIntegerColorMap.put(TankColor.MAGENTA, ConfigOptions.Colors.tankColorMagenta);
        stateColorToIntegerColorMap.put(TankColor.PURPLE, ConfigOptions.Colors.tankColorPurple);
        stateColorToIntegerColorMap.put(TankColor.BROWN, ConfigOptions.Colors.tankColorBrown);
        stateColorToIntegerColorMap.put(TankColor.GRAY, ConfigOptions.Colors.tankColorGray);
        stateColorToIntegerColorMap.put(TankColor.LIGHT_GRAY, ConfigOptions.Colors.tankColorLightGray);
        stateColorToIntegerColorMap.put(TankColor.BLACK, ConfigOptions.Colors.tankColorBlack);
    }

    public static int getIntegerColorFromState(TankColor colorState) {
        return stateColorToIntegerColorMap.get(colorState);
    }

    private static final HashMap<TankColor, Item> stateColorToDyeColorMap = new HashMap<>();

    static {
        stateColorToDyeColorMap.put(TankColor.WHITE, Items.WHITE_DYE);
        stateColorToDyeColorMap.put(TankColor.RED, Items.RED_DYE);
        stateColorToDyeColorMap.put(TankColor.ORANGE, Items.ORANGE_DYE);
        stateColorToDyeColorMap.put(TankColor.PINK, Items.PINK_DYE);
        stateColorToDyeColorMap.put(TankColor.YELLOW, Items.YELLOW_DYE);
        stateColorToDyeColorMap.put(TankColor.GREEN, Items.GREEN_DYE);
        stateColorToDyeColorMap.put(TankColor.LIME, Items.LIME_DYE);
        stateColorToDyeColorMap.put(TankColor.LIGHT_BLUE, Items.BLUE_DYE);
        stateColorToDyeColorMap.put(TankColor.CYAN, Items.CYAN_DYE);
        stateColorToDyeColorMap.put(TankColor.BLUE, Items.BLUE_DYE);
        stateColorToDyeColorMap.put(TankColor.MAGENTA, Items.MAGENTA_DYE);
        stateColorToDyeColorMap.put(TankColor.PURPLE, Items.MAGENTA_DYE);
        stateColorToDyeColorMap.put(TankColor.BROWN, Items.BROWN_DYE);
        stateColorToDyeColorMap.put(TankColor.GRAY, Items.GRAY_DYE);
        stateColorToDyeColorMap.put(TankColor.LIGHT_GRAY, Items.LIGHT_GRAY_DYE);
        stateColorToDyeColorMap.put(TankColor.BLACK, Items.BLACK_DYE);
    }

}
