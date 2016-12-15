package com.ewyboy.itank.common.utility;

public class Reference {

    public static final class ModInfo {
        public static final String ModID = "itank";
        public static final String ModName = "ITank";
        static final String VersionMajor = "1";
        static final String VersionMinor = "0";
        static final String VersionPatch = "0";
        static final String MinecraftVersion = "1.10.2";
        public static final String BuildVersion = VersionMajor + "." + VersionMinor + "." + VersionPatch + "-" + MinecraftVersion;
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
