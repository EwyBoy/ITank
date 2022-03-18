package com.ewyboy.itank.client;

import com.ewyboy.itank.ITank;
import com.ewyboy.itank.client.interfaces.IHasRenderType;
import com.ewyboy.itank.client.interfaces.IHasSpecialRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ITank.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientInitialization {

    @OnlyIn(Dist.CLIENT)
    public static void initRenderTypes(FMLClientSetupEvent ignoredEvent) {
        ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block instanceof IHasRenderType).forEach(
                block -> ItemBlockRenderTypes.setRenderLayer(block, ((IHasRenderType) block).getRenderType())
        );
    }

    @OnlyIn(Dist.CLIENT)
    public static void initSpecialRenders(FMLClientSetupEvent ignoredEvent) {
        ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block instanceof IHasSpecialRenderer).forEach(
                block -> ((IHasSpecialRenderer) block).initSpecialRenderer()
        );
    }

}
