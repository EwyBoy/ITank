package com.ewyboy.itank.config;

import com.ewyboy.itank.ITank;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = ITank.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHelper {


    public static ModConfig config;

    static void readConfigToMemory(ModConfig modConfig) {
        config = modConfig;
        Config config = ConfigHolder.config;

        ConfigOptions.Tanks.tankCapacity = config.tanks.tankCapacity.get();
        ConfigOptions.Tanks.retainFluidAfterExplosions = config.tanks.retainFluidAfterExplosions.get();

        ConfigOptions.Colors.tankColorWhite = config.colors.tankColorWhite.get();
        ConfigOptions.Colors.tankColorRed = config.colors.tankColorRed.get();
        ConfigOptions.Colors.tankColorOrange = config.colors.tankColorOrange.get();
        ConfigOptions.Colors.tankColorPink = config.colors.tankColorPink.get();
        ConfigOptions.Colors.tankColorYellow = config.colors.tankColorYellow.get();
        ConfigOptions.Colors.tankColorLime = config.colors.tankColorLime.get();
        ConfigOptions.Colors.tankColorGreen = config.colors.tankColorGreen.get();
        ConfigOptions.Colors.tankColorLightBlue = config.colors.tankColorLightBlue.get();
        ConfigOptions.Colors.tankColorCyan = config.colors.tankColorCyan.get();
        ConfigOptions.Colors.tankColorBlue = config.colors.tankColorBlue.get();
        ConfigOptions.Colors.tankColorMagenta = config.colors.tankColorMagenta.get();
        ConfigOptions.Colors.tankColorPurple = config.colors.tankColorPurple.get();
        ConfigOptions.Colors.tankColorBrown = config.colors.tankColorBrown.get();
        ConfigOptions.Colors.tankColorGray = config.colors.tankColorGray.get();
        ConfigOptions.Colors.tankColorLightGray = config.colors.tankColorLightGray.get();
        ConfigOptions.Colors.tankColorBlack = config.colors.tankColorBlack.get();

    }

    static void writeConfigFromMemory(ModConfig modConfig) {
        config = modConfig;
        Config config = ConfigHolder.config;

        config.tanks.tankCapacity.set(ConfigOptions.Tanks.tankCapacity);
        config.tanks.retainFluidAfterExplosions.set(ConfigOptions.Tanks.retainFluidAfterExplosions);

        config.colors.tankColorWhite.set(ConfigOptions.Colors.tankColorWhite);
        config.colors.tankColorRed.set(ConfigOptions.Colors.tankColorRed);
        config.colors.tankColorOrange.set(ConfigOptions.Colors.tankColorOrange);
        config.colors.tankColorPink.set(ConfigOptions.Colors.tankColorPink);
        config.colors.tankColorYellow.set(ConfigOptions.Colors.tankColorYellow);
        config.colors.tankColorLime.set(ConfigOptions.Colors.tankColorLime);
        config.colors.tankColorGreen.set(ConfigOptions.Colors.tankColorGreen);
        config.colors.tankColorLightBlue.set(ConfigOptions.Colors.tankColorLightBlue);
        config.colors.tankColorCyan.set(ConfigOptions.Colors.tankColorCyan);
        config.colors.tankColorBlue.set(ConfigOptions.Colors.tankColorBlue);
        config.colors.tankColorMagenta.set(ConfigOptions.Colors.tankColorMagenta);
        config.colors.tankColorPurple.set(ConfigOptions.Colors.tankColorPurple);
        config.colors.tankColorBrown.set(ConfigOptions.Colors.tankColorBrown);
        config.colors.tankColorGray.set(ConfigOptions.Colors.tankColorGray);
        config.colors.tankColorLightGray.set(ConfigOptions.Colors.tankColorLightGray);
        config.colors.tankColorBlack.set(ConfigOptions.Colors.tankColorBlack);
    }

    public static void reloadConfig() {
        readConfigToMemory(config);
    }

    public static void syncConfig() {
        writeConfigFromMemory(config);
    }

    public static void saveConfig() {
        config.save();
    }

    public static void reloadAndSaveConfig() {
        readConfigToMemory(config);
        config.save();
    }

    public static void saveAndReloadConfig() {
        config.save();
        readConfigToMemory(config);
    }

    public static void syncAndSaveConfig() {
        writeConfigFromMemory(config);
        config.save();
    }

    public static void saveAndSyncConfig() {
        config.save();
        writeConfigFromMemory(config);
    }

    public static void setValueAndSaveConfig(final String path, final Object newValue) {
        config.getConfigData().set(path, newValue);
        config.save();
        reloadConfig();
    }

    public static String getComment(final String path) {
        return config.getConfigData().getComment(path).replaceAll("\n", " - ");
    }

}
