package com.ewyboy.itank.proxy;

import com.ewyboy.bibliotheca.common.compatibilities.CompatibilityHandler;
import com.ewyboy.bibliotheca.common.loaders.BlockLoader;
import com.ewyboy.bibliotheca.common.loaders.TileEntityLoader;
import com.ewyboy.itank.common.loaders.ConfigLoader;
import com.ewyboy.itank.common.regsiter.Register;
import com.ewyboy.itank.common.utility.Reference;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

    public static FMLEventChannel packetHandler;

    public Side getSide(){return Side.SERVER;}

    public void preInit(FMLPreInitializationEvent event) {
        CompatibilityHandler.registerWaila();
        ConfigLoader.init(event.getSuggestedConfigurationFile());
        BlockLoader.init(Reference.ModInfo.MOD_ID, Register.Blocks.class);
        TileEntityLoader.init(Register.Tiles.class);
    }

    public void init(FMLInitializationEvent event) {}

    public void postInit(FMLPostInitializationEvent event){}
}
