package com.ewyboy.itank.common.utility;

public class Reference {

    public static final class ModInfo {
        public static final String MOD_ID = "itank";
        public static final String NAME = "ITank";
        static final String VERSION_MAJOR = "1";
        static final String VERSION_MINOR = "2";
        static final String VERSION_PATCH = "0";
        static final String MINECRAFT_VERSION = "1.12.2";
        public static final String BIBLIOTHECA_VERSION = "required-after:bibliotheca@[1.1.0-1.12.2,);";
        public static final String BUILD_VERSION = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH + "-" + MINECRAFT_VERSION;
    }

    public static final class Path {
        public static final String clientProxyPath = "com.ewyboy.itank.proxy.ClientProxy";
        public static final String commonProxyPath = "com.ewyboy.itank.proxy.CommonProxy";
    }

    public static final class Blocks {
        public static final String tank = "tank";
    }
}
