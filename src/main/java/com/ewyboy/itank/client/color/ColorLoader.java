package com.ewyboy.itank.client.color;

import com.ewyboy.itank.client.interfaces.IBlockColorizer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class ColorLoader {

    public static final HashMap<String, ColoredBlock> COLORED_BLOCKS = new HashMap<>();
    public static final HashMap<String, ColoredItem> COLORED_ITEMS = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public static void init(Class coloredBlockRegister, Class coloredItemsRegister) {
        if (coloredBlockRegister != null) registerColoredBlocks(coloredBlockRegister);
        if (coloredItemsRegister != null) registerColoredItems(coloredItemsRegister);
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerColoredBlocks(Class coloredBlockRegister) {
        try {
            for (Field field : coloredBlockRegister.getDeclaredFields()) {
                Object obj = field.get(null);
                if (obj instanceof ColoredBlock) {
                    ColoredBlock block = (ColoredBlock) obj;
                    String name = Objects.requireNonNull(block.blockToColor().getName()).toString();
                    registerColoredBlock(block, name);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerColoredBlock(ColoredBlock coloredBlock, String name) {
        COLORED_BLOCKS.put(name, coloredBlock);
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        blockColors.register(coloredBlock, ((IBlockColorizer) coloredBlock).blockToColor());
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerColoredItems(Class coloredItemRegister) {
        try {
            for (Field field : coloredItemRegister.getDeclaredFields()) {
                Object obj = field.get(null);
                if (obj instanceof ColoredItem) {
                    ColoredItem item = (ColoredItem) obj;
                    registerColoredItem(item);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerColoredItem(ColoredItem coloredItem) {
        COLORED_ITEMS.put(Objects.requireNonNull(coloredItem.itemToColor().asItem().getName(new ItemStack(coloredItem.itemToColor().asItem()))).toString(), coloredItem);

        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        ItemColors itemColors = Minecraft.getInstance().getItemColors();

        ItemColor getColorFromBlock = (stack, tintIndex) -> {
            BlockState state = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
            return blockColors.getColor(state, null, null, tintIndex);
        };

        itemColors.register(getColorFromBlock, coloredItem.itemToColor());
    }

}
