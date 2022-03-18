package com.ewyboy.itank.client.color;

import com.ewyboy.itank.client.interfaces.IItemColorizer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ColoredItem implements IItemColorizer {

    private final Item item;
    private final int colorIndex;

    public ColoredItem(Item item, int colorIndex) {
        this.item = item;
        this.colorIndex = colorIndex;
    }

    public Item getItem() {
        return item;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    @Override
    public Item itemToColor() {
        return getItem();
    }

    @Override
    public int getColor(ItemStack itemStack, int tint) {
        return getColorIndex();
    }
}
