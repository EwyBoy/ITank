package com.ewyboy.itank.common.loaders;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigLoader {

    public static boolean debugMode;

    public static void init (File file) {
        Configuration config = new Configuration(file);

        config.load();
            debugMode = config.getBoolean("Debug Mode", "Debug", true, "Debug Mode");
        config.save();
    }
}
