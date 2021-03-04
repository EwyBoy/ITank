package com.ewyboy.itank.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static final ForgeConfigSpec settingSpec;
    public static final Config.Settings SETTINGS;

    static {
        Pair<Settings, ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(Config.Settings::new);
        settingSpec = specPair.getRight();
        SETTINGS = specPair.getLeft();
    }

    public static class Settings {

        public final ForgeConfigSpec.ConfigValue<Integer> tankCapacity;

        Settings(ForgeConfigSpec.Builder builder) {
            builder.comment("ITank - Config File").push("SETTINGS");
                this.tankCapacity =
                    builder
                            .comment("Sets tank capacity : 1 Bucket = 1000mB - [Max: " + Integer.MAX_VALUE + "mB]")
                            .translation("itank.config.tankCapacity")
                            .defineInRange("tankCapacity", 8000, 1000, Integer.MAX_VALUE);
            builder.pop();
        }
    }

}
