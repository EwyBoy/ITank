package com.ewyboy.itank.common.loaders;

import com.ewyboy.itank.common.regsiter.Register;
import com.ewyboy.itank.common.utility.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.ModInfo.MOD_ID)
public class CreativeTabLoader {

    public static CreativeTabs ITank = new CreativeTabs(Reference.ModInfo.NAME) {
        public ItemStack getIconItemStack(){return new ItemStack(Register.Blocks.tank);}
        @Override
        public ItemStack getTabIconItem() {return null;}
    };
}
