package com.ewyboy.itank;

import com.electronwill.nightconfig.core.Config;
import com.ewyboy.itank.client.ClientInitialization;
import com.ewyboy.itank.client.color.ColorLoader;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.config.ConfigHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
        MinecraftForge.EVENT_BUS.register(this);
        MOD_BUS.addListener(this :: addCreativeModeTab);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ColorLoader.init(Register.COLORED_BLOCKS.class, Register.COLORED_ITEMS.class);
    }

    public static CreativeModeTab ITANK_TAB;

    @SubscribeEvent
    public void registerCreativeTab(CreativeModeTabEvent.Register event) {
        ITANK_TAB = event.registerCreativeModeTab(new ResourceLocation(MOD_ID, "itank_tab"),
                builder -> builder
                        .title(Component.literal("ITank"))
                        .icon(() -> new ItemStack(Register.BLOCK.TANK.get()))
                        .displayItems((params, output) -> {
                            output.accept(Register.BLOCK.TANK.get());
                            output.accept(Register.BLOCK.TANK_WHITE.get());
                            output.accept(Register.BLOCK.TANK_ORANGE.get());
                            output.accept(Register.BLOCK.TANK_MAGENTA.get());
                            output.accept(Register.BLOCK.TANK_LIGHT_BLUE.get());
                            output.accept(Register.BLOCK.TANK_YELLOW.get());
                            output.accept(Register.BLOCK.TANK_LIME.get());
                            output.accept(Register.BLOCK.TANK_PINK.get());
                            output.accept(Register.BLOCK.TANK_LIGHT_GRAY.get());
                            output.accept(Register.BLOCK.TANK_CYAN.get());
                            output.accept(Register.BLOCK.TANK_PURPLE.get());
                            output.accept(Register.BLOCK.TANK_BLUE.get());
                            output.accept(Register.BLOCK.TANK_BROWN.get());
                            output.accept(Register.BLOCK.TANK_GREEN.get());
                            output.accept(Register.BLOCK.TANK_RED.get());
                            output.accept(Register.BLOCK.TANK_BLACK.get());
                        }).build());
    }

    private void addCreativeModeTab(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab().equals(ITANK_TAB)) {
            event.accept(Register.BLOCK.TANK);
            event.accept(Register.BLOCK.TANK_WHITE);
            event.accept(Register.BLOCK.TANK_ORANGE);
            event.accept(Register.BLOCK.TANK_MAGENTA);
            event.accept(Register.BLOCK.TANK_LIGHT_BLUE);
            event.accept(Register.BLOCK.TANK_YELLOW);
            event.accept(Register.BLOCK.TANK_LIME);
            event.accept(Register.BLOCK.TANK_PINK);
            event.accept(Register.BLOCK.TANK_LIGHT_GRAY);
            event.accept(Register.BLOCK.TANK_CYAN);
            event.accept(Register.BLOCK.TANK_PURPLE);
            event.accept(Register.BLOCK.TANK_BLUE);
            event.accept(Register.BLOCK.TANK_BROWN);
            event.accept(Register.BLOCK.TANK_GREEN);
            event.accept(Register.BLOCK.TANK_RED);
            event.accept(Register.BLOCK.TANK_BLACK);
        }
    }

}