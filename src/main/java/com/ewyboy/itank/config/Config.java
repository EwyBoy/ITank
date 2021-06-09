package com.ewyboy.itank.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static class Tanks {
        ForgeConfigSpec.IntValue tankCapacity;
    }

    public static class Colors {
        ForgeConfigSpec.IntValue tankColorWhite;
        ForgeConfigSpec.IntValue tankColorRed;
        ForgeConfigSpec.IntValue tankColorOrange;
        ForgeConfigSpec.IntValue tankColorPink;
        ForgeConfigSpec.IntValue tankColorYellow;
        ForgeConfigSpec.IntValue tankColorLime;
        ForgeConfigSpec.IntValue tankColorGreen;
        ForgeConfigSpec.IntValue tankColorLightBlue;
        ForgeConfigSpec.IntValue tankColorCyan;
        ForgeConfigSpec.IntValue tankColorBlue;
        ForgeConfigSpec.IntValue tankColorMagenta;
        ForgeConfigSpec.IntValue tankColorPurple;
        ForgeConfigSpec.IntValue tankColorBrown;
        ForgeConfigSpec.IntValue tankColorGray;
        ForgeConfigSpec.IntValue tankColorLightGray;
        ForgeConfigSpec.IntValue tankColorBlack;
    }


    public final Tanks tanks = new Tanks();
    public final Colors colors = new Colors();


    Config(final ForgeConfigSpec.Builder builder) {
        builder.comment("ITank - Config File").push("SETTINGS");
        {

            builder.comment("Tank Settings").push("Tanks");
            {
                tanks.tankCapacity = builder
                        .comment("Sets tank capacity : 1 Bucket = 1000mB - [Max: " + Integer.MAX_VALUE + "mB]")
                        .translation("itank.config.tanks.tankCapacity")
                        .defineInRange("tank_capacity", 8000, 1000, Integer.MAX_VALUE)
                ;
            }
            builder.pop();

            builder.comment("Color Settings").push("Colors");
            {
                colors.tankColorWhite = builder
                        .comment("Configure white tanks color -> Default Hex: 0xffffff")
                        .translation("itank.config.color.tankColorWhite")
                        .defineInRange("tank_color_white", 0xffffff, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorRed = builder
                        .comment("Configure red tanks color -> Default Hex: 0xcd352c")
                        .translation("itank.config.color.tankColorRed")
                        .defineInRange("tank_color_red", 0xcd352c, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorOrange = builder
                        .comment("Configure orange tanks color -> Default Hex: 0xff6d00")
                        .translation("itank.config.color.tankColorOrange")
                        .defineInRange("tank_color_orange", 0xff6d00, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorPink = builder
                        .comment("Configure pink tanks color -> Default Hex: 0xff4e87")
                        .translation("itank.config.color.tankColorPink")
                        .defineInRange("tank_color_pink", 0xff4e87, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorYellow = builder
                        .comment("Configure yellow tanks color -> Default Hex: 0xceae27")
                        .translation("itank.config.color.tankColorYellow")
                        .defineInRange("tank_color_yellow", 0xceae27, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorLime = builder
                        .comment("Configure lime tanks color -> Default Hex: 0x83c825")
                        .translation("itank.config.color.tankColorLime")
                        .defineInRange("tank_color_lime", 0x83c825, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorGreen = builder
                        .comment("Configure green tanks color -> Default Hex: 0x546f17")
                        .translation("itank.config.color.tankColorGreen")
                        .defineInRange("tank_color_green", 0x546f17, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorLightBlue = builder
                        .comment("Configure light blue tanks color -> Default Hex: 0x2ea5c7")
                        .translation("itank.config.color.tankColorLightBlue")
                        .defineInRange("tank_color_light_blue", 0x2ea5c7, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorCyan = builder
                        .comment("Configure cyan tanks color -> Default Hex: 0x0cffff")
                        .translation("itank.config.color.tankColorCyan")
                        .defineInRange("tank_color_cyan", 0x0cffff, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorBlue = builder
                        .comment("Configure blue tanks color -> Default Hex: 0x3035a4")
                        .translation("itank.config.color.tankColorBlue")
                        .defineInRange("tank_color_blue", 0x3035a4, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorMagenta = builder
                        .comment("Configure magenta tanks color -> Default Hex: 0xbc37b6")
                        .translation("itank.config.color.tankColorMagenta")
                        .defineInRange("tank_color_magenta", 0xbc37b6, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorPurple = builder
                        .comment("Configure purple tanks color -> Default Hex: 0x68268d")
                        .translation("itank.config.color.tankColorPurple")
                        .defineInRange("tank_color_purple", 0x68268d, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorBrown = builder
                        .comment("Configure brown tanks color -> Default Hex: 0x49311d")
                        .translation("itank.config.color.tankColorBrown")
                        .defineInRange("tank_color_brown", 0x49311d, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorGray = builder
                        .comment("Configure gray tanks color -> Default Hex: 0x808080")
                        .translation("itank.config.color.tankColorGray")
                        .defineInRange("tank_color_gray", 0x808080, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorLightGray = builder
                        .comment("Configure gray tanks color -> Default Hex: 0xc0c0c0")
                        .translation("itank.config.color.tankColorGray")
                        .defineInRange("tank_color_light_gray", 0xc0c0c0, 0x000000, 0xFFFFFF)
                ;
                colors.tankColorBlack = builder
                        .comment("Configure black tanks color -> Default Hex: 0x151517")
                        .translation("itank.config.color.tankColorBlack")
                        .defineInRange("tank_color_black", 0x151517, 0x000000, 0xFFFFFF)
                ;

            }
            builder.pop();

        }
        builder.pop();
    }
}
