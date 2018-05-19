package com.ewyboy.itank;

import com.ewyboy.itank.common.utility.Reference;
import com.ewyboy.itank.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/** Created by EwyBoy **/
@Mod(modid = Reference.ModInfo.MOD_ID, name = Reference.ModInfo.MOD_ID, version = Reference.ModInfo.BUILD_VERSION, dependencies = Reference.ModInfo.BIBLIOTHECA_VERSION)
public class ITank {

    @Mod.Instance(Reference.ModInfo.MOD_ID)
    public static ITank instance;

    @SidedProxy(modId = Reference.ModInfo.MOD_ID, clientSide = Reference.Path.clientProxyPath, serverSide = Reference.Path.commonProxyPath)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}