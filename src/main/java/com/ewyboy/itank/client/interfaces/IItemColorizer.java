package com.ewyboy.itank.client.interfaces;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IItemColorizer extends ItemColor {

    Item itemToColor();

    int getColor(ItemStack itemStack, int tint);
}
