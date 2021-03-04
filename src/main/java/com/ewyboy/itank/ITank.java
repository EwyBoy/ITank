package com.ewyboy.itank;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.config.Config;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ITank.MODID)
@Mod.EventBusSubscriber(modid = ITank.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ITank {

    public static final String MODID = "itank";

    public ITank() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.settingSpec);
        ContentLoader.init(ITank.MODID, ITank.itemGroup, Register.BLOCK.class, Register.ITEM.class, Register.TILE.class);
    }

    public static final ItemGroup itemGroup = new ItemGroup(ITank.MODID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Register.BLOCK.TANK);
        }
    };

}