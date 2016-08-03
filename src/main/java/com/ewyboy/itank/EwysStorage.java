package com.ewyboy.ewysstorage;

import com.ewyboy.ewysstorage.common.compatibilities.CompatibilityHandler;
import com.ewyboy.ewysstorage.common.loaders.BlockLoader;
import com.ewyboy.ewysstorage.common.utility.Logger;
import com.ewyboy.ewysstorage.common.utility.Reference;
import com.ewyboy.ewysstorage.proxy.CommonProxy;
import com.google.common.base.Stopwatch;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.concurrent.TimeUnit;

/** Created by EwyBoy **/
@Mod(modid = Reference.ModInfo.ModID, name = Reference.ModInfo.ModName, version = Reference.ModInfo.BuildVersion)
public class EwysStorage {

    public static FMLEventChannel packetHandler;

    @Mod.Instance(Reference.ModInfo.ModID)
    public static EwysStorage instance;

    @SidedProxy(modId = Reference.ModInfo.ModID, clientSide = Reference.Path.clientProxyPath, serverSide = Reference.Path.commonProxyPath)
    public static CommonProxy proxy;

    public static Configuration configuration;

    private long launchTime;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Pre-Initialization started");
                packetHandler = NetworkRegistry.INSTANCE.newEventDrivenChannel(Reference.ModInfo.ModID);
                CompatibilityHandler.registerWaila();
                BlockLoader.loadBlocks();
                proxy.loadModels();
                proxy.loadItemModels();
                launchTime += watch.elapsed(TimeUnit.MILLISECONDS);
            Logger.info("Pre-Initialization finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        Logger.info("Pre-Initialization process successfully done");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Initialization started");
                launchTime += watch.elapsed(TimeUnit.MILLISECONDS);
            Logger.info("Initialization finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        Logger.info("Initialization process successfully done");
    }

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Post-Initialization started");
                launchTime += watch.elapsed(TimeUnit.MILLISECONDS);
            Logger.info("Post-Initialization finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
            Logger.info("Post-Initialization process successfully done");
        Logger.info("Total Initialization time was " + launchTime);
    }
}