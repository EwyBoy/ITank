package com.ewyboy.itank.common.utility;

public class Reference {

    public static final class ModInfo {
        public static final String MODID = "itank";
        public static final String NAME = "ITank";
        static final String VERSION_MAJOR = "1";
        static final String VERSION_MINOR = "1";
        static final String VERSION_PATCH = "0";
        static final String MINECRAFT_VERSION = "1.10.2";
        public static final String BUILD_VERSION = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH + "-" + MINECRAFT_VERSION;
    }

    public static final class Path {
        public static final String wailaPath = "com.ewyboy.itank.common.compatibilities.waila.WailaCompatibility.load";
        public static final String clientProxyPath = "com.ewyboy.itank.proxy.ClientProxy";
        public static final String commonProxyPath = "com.ewyboy.itank.proxy.CommonProxy";
    }

    public static final class Blocks {
        public static final String tank = "tank";
    }
}
