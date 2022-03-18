package com.ewyboy.itank;

import com.electronwill.nightconfig.core.Config;

import com.ewyboy.itank.client.ClientInitialization;
import com.ewyboy.itank.client.color.ColorLoader;
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
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

@Mod(ITank.MOD_ID)
@Mod.EventBusSubscriber(modid = ITank.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ITank {

    public static final String MOD_ID = "itank";

    public ITank() {
        Config.setInsertionOrderPreserved(true);
        IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        ConfigHolder.init();
        Register.init();
        MOD_BUS.addListener(this :: clientSetup);
        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientInitialization :: initRenderTypes);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientInitialization :: initSpecialRenders);
            return null;
        });
        MinecraftForge.EVENT_BUS.register(CommandCenter.class);
        MinecraftForge.EVENT_BUS.addListener(this :: registerCommands);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ColorLoader.init(Register.COLORED_BLOCKS.class, Register.COLORED_ITEMS.class);
    }

    public void registerCommands(RegisterCommandsEvent event) {
        new CommandCenter(event.getDispatcher());
    }

    public static final CreativeModeTab itemGroup = new CreativeModeTab(ITank.MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Register.BLOCK.TANK.get());
        }
    };

}