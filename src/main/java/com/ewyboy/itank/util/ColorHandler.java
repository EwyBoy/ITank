package com.ewyboy.itank.util;

import com.ewyboy.itank.common.content.tank.TankBlock;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.common.states.TankColor;
import com.ewyboy.itank.config.ConfigOptions;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

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

    private static final HashMap<TankColor, TankBlock> stateColorToBlockColorMap = new HashMap<>();

    static {
        stateColorToBlockColorMap.put(TankColor.WHITE, Register.BLOCK.TANK_WHITE.get());
        stateColorToBlockColorMap.put(TankColor.RED, Register.BLOCK.TANK_RED.get());
        stateColorToBlockColorMap.put(TankColor.ORANGE, Register.BLOCK.TANK_ORANGE.get());
        stateColorToBlockColorMap.put(TankColor.PINK, Register.BLOCK.TANK_PINK.get());
        stateColorToBlockColorMap.put(TankColor.YELLOW, Register.BLOCK.TANK_YELLOW.get());
        stateColorToBlockColorMap.put(TankColor.GREEN, Register.BLOCK.TANK_GREEN.get());
        stateColorToBlockColorMap.put(TankColor.LIME, Register.BLOCK.TANK_LIME.get());
        stateColorToBlockColorMap.put(TankColor.LIGHT_BLUE, Register.BLOCK.TANK_LIGHT_BLUE.get());
        stateColorToBlockColorMap.put(TankColor.CYAN, Register.BLOCK.TANK_CYAN.get());
        stateColorToBlockColorMap.put(TankColor.BLUE, Register.BLOCK.TANK_BLUE.get());
        stateColorToBlockColorMap.put(TankColor.MAGENTA, Register.BLOCK.TANK_MAGENTA.get());
        stateColorToBlockColorMap.put(TankColor.PURPLE, Register.BLOCK.TANK_PURPLE.get());
        stateColorToBlockColorMap.put(TankColor.BROWN, Register.BLOCK.TANK_BROWN.get());
        stateColorToBlockColorMap.put(TankColor.GRAY, Register.BLOCK.TANK.get());
        stateColorToBlockColorMap.put(TankColor.LIGHT_GRAY, Register.BLOCK.TANK_LIGHT_GRAY.get());
        stateColorToBlockColorMap.put(TankColor.BLACK, Register.BLOCK.TANK_BLACK.get());
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


    private static final HashMap<TankColor, MapColor> stateColorToMaterialColorMap = new HashMap<>();

    static {
        stateColorToMaterialColorMap.put(TankColor.WHITE, MapColor.SNOW);
        stateColorToMaterialColorMap.put(TankColor.RED, MapColor.COLOR_RED);
        stateColorToMaterialColorMap.put(TankColor.ORANGE, MapColor.COLOR_ORANGE);
        stateColorToMaterialColorMap.put(TankColor.PINK, MapColor.COLOR_PINK);
        stateColorToMaterialColorMap.put(TankColor.YELLOW, MapColor.COLOR_YELLOW);
        stateColorToMaterialColorMap.put(TankColor.GREEN, MapColor.COLOR_GREEN);
        stateColorToMaterialColorMap.put(TankColor.LIME, MapColor.COLOR_LIGHT_GREEN);
        stateColorToMaterialColorMap.put(TankColor.LIGHT_BLUE, MapColor.COLOR_BLUE);
        stateColorToMaterialColorMap.put(TankColor.CYAN, MapColor.COLOR_CYAN);
        stateColorToMaterialColorMap.put(TankColor.BLUE, MapColor.COLOR_BLUE);
        stateColorToMaterialColorMap.put(TankColor.MAGENTA, MapColor.COLOR_MAGENTA);
        stateColorToMaterialColorMap.put(TankColor.PURPLE, MapColor.COLOR_MAGENTA);
        stateColorToMaterialColorMap.put(TankColor.BROWN, MapColor.COLOR_BROWN);
        stateColorToMaterialColorMap.put(TankColor.GRAY, MapColor.COLOR_GRAY);
        stateColorToMaterialColorMap.put(TankColor.LIGHT_GRAY, MapColor.COLOR_LIGHT_GRAY);
        stateColorToMaterialColorMap.put(TankColor.BLACK, MapColor.COLOR_BLACK);
    }

    public static MapColor getMaterialColorFromState(TankColor color) {
        return stateColorToMaterialColorMap.get(color);
    }

    public static TankBlock getBlockColorFromState(TankColor color) {
        return stateColorToBlockColorMap.get(color);
    }

    public static int getIntegerColorFromState(TankColor colorState) {
        return stateColorToIntegerColorMap.get(colorState);
    }

}
