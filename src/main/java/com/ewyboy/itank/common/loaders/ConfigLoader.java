package com.ewyboy.itank.common.loaders;

import com.ewyboy.itank.common.utility.Reference;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigLoader {

    public static int maxTankCapacity;

    public static void init(File file) {
        Configuration config = new Configuration(file);

        config.load();
        maxTankCapacity = config.getInt("Max Tank Capacity", Reference.ModInfo.MOD_NAME, 8000, 0, Integer.MAX_VALUE,
                "Sets the maximum amount of liquid possible to store per tank [mB]");
        config.save();
    }
}
