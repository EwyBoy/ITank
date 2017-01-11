package com.ewyboy.itank.common.utility;

public final class Reference {

    public final class ModInfo {

        public static final String MOD_ID = "itank";
        public static final String MOD_NAME = "ITank";
        public static final String VERSION_MAJOR = "1";
        public static final String VERSION_MINOR = "0";
        public static final String VERSION_PATCH = "0";
        public static final String MINECRAFT_VERSION = "1.10.2";
        public static final String BuildVersion = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH + "-" + MINECRAFT_VERSION;
    }

    public final class Path {

        public static final String WAILA_PATH = "com.ewyboy.itank.common.compatibilities.waila.WailaCompatibility.load";
        public static final String CLIENT_PROXY = "com.ewyboy.itank.proxy.ClientProxy";
        public static final String COMMON_PROXY = "com.ewyboy.itank.proxy.CommonProxy";
    }

    public final class Blocks {

        public static final String tank = "tank";
    }
}
