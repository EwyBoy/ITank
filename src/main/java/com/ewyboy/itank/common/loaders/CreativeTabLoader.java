package com.ewyboy.ewysstorage.common.loaders;

import com.ewyboy.ewysstorage.common.utility.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.ModInfo.ModID)
public class CreativeTabLoader {

    public static CreativeTabs EwysStorage = new CreativeTabs(Reference.ModInfo.ModName) {
        public ItemStack getIconItemStack(){return new ItemStack(BlockLoader.tank);}
        @Override
        public Item getTabIconItem() {return null;}
    };
}
