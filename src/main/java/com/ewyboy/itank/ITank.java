package com.ewyboy.itank;

import com.electronwill.nightconfig.core.Config;
import com.ewyboy.itank.client.ClientInitialization;
import com.ewyboy.itank.client.color.ColorLoader;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.config.ConfigHolder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod(ITank.MOD_ID)
@Mod.EventBusSubscriber(modid = ITank.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ITank {

    public static final String MOD_ID = "itank";

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static RegistryObject<CreativeModeTab> TAB = TABS.register(MOD_ID, () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(Register.BLOCK.TANK.get().asItem()))
                    .title(Component.literal("ITank"))
                    .displayItems((params, output) -> Register.BLOCK.TANKS.forEach(tank -> output.accept(new ItemStack(tank.get().asItem()))))
                    .build()
    );

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
        TABS.register(MOD_BUS);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ColorLoader.init(Register.COLORED_BLOCKS.class, Register.COLORED_ITEMS.class);
    }

}