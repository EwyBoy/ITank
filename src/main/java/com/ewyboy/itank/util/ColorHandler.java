package com.ewyboy.itank.util;

import com.ewyboy.itank.common.states.TankColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.awt.*;
import java.util.HashMap;

public class ColorHandler {

    public static final HashMap<TankColor, Integer> stateColorToIntegerColorMap = new HashMap<>();

    static {
        stateColorToIntegerColorMap.put(TankColor.WHITE, Color.WHITE.getRGB());
        stateColorToIntegerColorMap.put(TankColor.RED, 0xe64040);
        stateColorToIntegerColorMap.put(TankColor.ORANGE, Color.ORANGE.getRGB());
        stateColorToIntegerColorMap.put(TankColor.PINK, Color.PINK.getRGB());
        stateColorToIntegerColorMap.put(TankColor.YELLOW, Color.YELLOW.getRGB());
        stateColorToIntegerColorMap.put(TankColor.LIME, Color.GREEN.brighter().getRGB());
        stateColorToIntegerColorMap.put(TankColor.GREEN, Color.GREEN.getRGB());
        stateColorToIntegerColorMap.put(TankColor.LIGHT_BLUE, Color.BLUE.brighter().getRGB());
        stateColorToIntegerColorMap.put(TankColor.CYAN, Color.CYAN.getRGB());
        stateColorToIntegerColorMap.put(TankColor.BLUE, Color.BLUE.getRGB());
        stateColorToIntegerColorMap.put(TankColor.MAGENTA, Color.MAGENTA.getRGB());
        stateColorToIntegerColorMap.put(TankColor.PURPLE, Color.MAGENTA.darker().getRGB());
        stateColorToIntegerColorMap.put(TankColor.BROWN, 0xffAAAA);
        stateColorToIntegerColorMap.put(TankColor.GRAY, Color.GRAY.getRGB());
        stateColorToIntegerColorMap.put(TankColor.LIGHT_GRAY, Color.LIGHT_GRAY.getRGB());
        stateColorToIntegerColorMap.put(TankColor.BLACK, Color.BLACK.getRGB());
    }

    public static final HashMap<TankColor, Item> stateColorToDyeColorMap = new HashMap<>();

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
