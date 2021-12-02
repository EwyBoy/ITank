package com.ewyboy.itank;

import com.electronwill.nightconfig.core.Config;
import com.ewyboy.bibliotheca.client.color.ColorLoader;
import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.config.ConfigHolder;
import com.ewyboy.itank.server.CommandCenter;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ITank.MOD_ID)
@Mod.EventBusSubscriber(modid = ITank.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ITank {

    public static final String MOD_ID = "itank";

    public ITank() {
        Config.setInsertionOrderPreserved(true);
        IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        ConfigHolder.init();
        ContentLoader.init(ITank.MOD_ID, ITank.itemGroup, Register.BLOCK.class, Register.ITEM.class, Register.TILE.class);
        MOD_BUS.addListener(this :: clientSetup);
        MinecraftForge.EVENT_BUS.register(CommandCenter.class);
        MinecraftForge.EVENT_BUS.addListener(this :: registerCommands);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ColorLoader.init(MOD_ID, Register.COLORED_BLOCKS.class, Register.COLORED_ITEMS.class);
    }

    public void registerCommands(RegisterCommandsEvent event) {
        new CommandCenter(event.getDispatcher());
    }

    public static final CreativeModeTab itemGroup = new CreativeModeTab(ITank.MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Register.BLOCK.TANK);
        }
    };

}