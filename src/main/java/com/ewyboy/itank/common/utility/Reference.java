package com.ewyboy.ewysstorage.common.utility;

public class Reference {

    public static final class ModInfo {
        public static final String ModID = "ewysstorage";
        public static final String ModName = "Ewy's Storage";
        static final String VersionMajor = "1";
        static final String VersionMinor = "0";
        static final String VersionPatch = "0";
        public static final String BuildVersion = VersionMajor + "." + VersionMinor + "." + VersionPatch;
        public static final String MinecraftVersion = "1.10.2";
    }

    public static final class Path {
        public static final String wailaPath = "com.ewyboy.ewysstorage.common.compatibilities.waila.WailaCompatibility.load";
        public static final String clientProxyPath = "com.ewyboy.ewysstorage.proxy.ClientProxy";
        public static final String commonProxyPath = "com.ewyboy.ewysstorage.proxy.CommonProxy";
    }

    public static final class Blocks {
        public static final String tank = "tank";
    }
}
