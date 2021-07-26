package com.ewyboy.itank.common.register;

import com.ewyboy.bibliotheca.client.color.ColoredBlock;
import com.ewyboy.bibliotheca.client.color.ColoredItem;
import com.ewyboy.itank.ITank;
import com.ewyboy.itank.common.content.tank.*;
import com.ewyboy.itank.common.states.TankColor;
import com.ewyboy.itank.util.ColorHandler;
import com.google.common.collect.Sets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.Set;

public class Register {

    public static final class BLOCK {
        
        public static final TankBlock TANK = new TankBlock(TankColor.GRAY);
        public static final TankBlock TANK_WHITE = new TankBlock(TankColor.WHITE);
        public static final TankBlock TANK_ORANGE = new TankBlock(TankColor.ORANGE);
        public static final TankBlock TANK_MAGENTA = new TankBlock(TankColor.MAGENTA);
        public static final TankBlock TANK_LIGHT_BLUE = new TankBlock(TankColor.LIGHT_BLUE);
        public static final TankBlock TANK_YELLOW = new TankBlock(TankColor.YELLOW);
        public static final TankBlock TANK_LIME = new TankBlock(TankColor.LIME);
        public static final TankBlock TANK_PINK = new TankBlock(TankColor.PINK);
        public static final TankBlock TANK_LIGHT_GRAY = new TankBlock(TankColor.LIGHT_GRAY);
        public static final TankBlock TANK_CYAN = new TankBlock(TankColor.CYAN);
        public static final TankBlock TANK_PURPLE = new TankBlock(TankColor.PURPLE);
        public static final TankBlock TANK_BLUE = new TankBlock(TankColor.BLUE);
        public static final TankBlock TANK_BROWN = new TankBlock(TankColor.BROWN);
        public static final TankBlock TANK_GREEN = new TankBlock(TankColor.GREEN);
        public static final TankBlock TANK_RED = new TankBlock(TankColor.RED);
        public static final TankBlock TANK_BLACK = new TankBlock(TankColor.BLACK);

    }

    public static final class ITEM {


        public static final TankItem TANK = new TankItem(BLOCK.TANK, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_WHITE = new TankItem(BLOCK.TANK_WHITE, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_ORANGE = new TankItem(BLOCK.TANK_ORANGE, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_MAGENTA = new TankItem(BLOCK.TANK_MAGENTA, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_LIGHT_BLUE = new TankItem(BLOCK.TANK_LIGHT_BLUE, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_YELLOW = new TankItem(BLOCK.TANK_YELLOW, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_LIME = new TankItem(BLOCK.TANK_LIME, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_PINK = new TankItem(BLOCK.TANK_PINK, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_LIGHT_GRAY = new TankItem(BLOCK.TANK_LIGHT_GRAY, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_CYAN = new TankItem(BLOCK.TANK_CYAN, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_PURPLE = new TankItem(BLOCK.TANK_PURPLE, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_BLUE = new TankItem(BLOCK.TANK_BLUE, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_BROWN = new TankItem(BLOCK.TANK_BROWN, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_GREEN = new TankItem(BLOCK.TANK_GREEN, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_RED = new TankItem(BLOCK.TANK_RED, new Item.Properties().tab(ITank.itemGroup));
        public static final TankItem TANK_BLACK = new TankItem(BLOCK.TANK_BLACK, new Item.Properties().tab(ITank.itemGroup));

    }

    private static final Set<Block> TANKS = new HashSet<>();

    static {
        TANKS.add(BLOCK.TANK);
        TANKS.add(BLOCK.TANK_WHITE);
        TANKS.add(BLOCK.TANK_ORANGE);
        TANKS.add(BLOCK.TANK_MAGENTA);
        TANKS.add(BLOCK.TANK_LIGHT_BLUE);
        TANKS.add(BLOCK.TANK_YELLOW);
        TANKS.add(BLOCK.TANK_LIME );
        TANKS.add(BLOCK.TANK_PINK );
        TANKS.add(BLOCK.TANK_LIGHT_GRAY);
        TANKS.add(BLOCK.TANK_CYAN );
        TANKS.add(BLOCK.TANK_PURPLE);
        TANKS.add(BLOCK.TANK_BLUE );
        TANKS.add(BLOCK.TANK_BROWN);
        TANKS.add(BLOCK.TANK_GREEN);
        TANKS.add(BLOCK.TANK_RED);
        TANKS.add(BLOCK.TANK_BLACK);
    }

    public static final class TILE {
        public static final BlockEntityType<TankTile> TANK = new BlockEntityType<>(TankTile :: new, Sets.newHashSet(TANKS), null);
    }

    public static final class COLORED_BLOCKS {

        public static final ColoredBlock TANK = new ColoredBlock(BLOCK.TANK, ColorHandler.getIntegerColorFromState(TankColor.GRAY));
        public static final ColoredBlock TANK_WHITE = new ColoredBlock(BLOCK.TANK_WHITE, ColorHandler.getIntegerColorFromState(TankColor.WHITE));
        public static final ColoredBlock TANK_ORANGE = new ColoredBlock(BLOCK.TANK_ORANGE, ColorHandler.getIntegerColorFromState(TankColor.ORANGE));
        public static final ColoredBlock TANK_MAGENTA = new ColoredBlock(BLOCK.TANK_MAGENTA, ColorHandler.getIntegerColorFromState(TankColor.MAGENTA));
        public static final ColoredBlock TANK_LIGHT_BLUE = new ColoredBlock(BLOCK.TANK_LIGHT_BLUE, ColorHandler.getIntegerColorFromState(TankColor.LIGHT_BLUE));
        public static final ColoredBlock TANK_YELLOW = new ColoredBlock(BLOCK.TANK_YELLOW, ColorHandler.getIntegerColorFromState(TankColor.YELLOW));
        public static final ColoredBlock TANK_LIME  = new ColoredBlock(BLOCK.TANK_LIME, ColorHandler.getIntegerColorFromState(TankColor.LIME));
        public static final ColoredBlock TANK_PINK  = new ColoredBlock(BLOCK.TANK_PINK, ColorHandler.getIntegerColorFromState(TankColor.PINK));
        public static final ColoredBlock TANK_LIGHT_GRAY = new ColoredBlock(BLOCK.TANK_LIGHT_GRAY, ColorHandler.getIntegerColorFromState(TankColor.LIGHT_GRAY));
        public static final ColoredBlock TANK_CYAN  = new ColoredBlock(BLOCK.TANK_CYAN, ColorHandler.getIntegerColorFromState(TankColor.CYAN));
        public static final ColoredBlock TANK_PURPLE = new ColoredBlock(BLOCK.TANK_PURPLE, ColorHandler.getIntegerColorFromState(TankColor.PURPLE));
        public static final ColoredBlock TANK_BLUE  = new ColoredBlock(BLOCK.TANK_BLUE, ColorHandler.getIntegerColorFromState(TankColor.BLUE));
        public static final ColoredBlock TANK_BROWN = new ColoredBlock(BLOCK.TANK_BROWN, ColorHandler.getIntegerColorFromState(TankColor.BROWN));
        public static final ColoredBlock TANK_GREEN = new ColoredBlock(BLOCK.TANK_GREEN, ColorHandler.getIntegerColorFromState(TankColor.GREEN));
        public static final ColoredBlock TANK_RED = new ColoredBlock(BLOCK.TANK_RED, ColorHandler.getIntegerColorFromState(TankColor.RED));
        public static final ColoredBlock TANK_BLACK = new ColoredBlock(BLOCK.TANK_BLACK, ColorHandler.getIntegerColorFromState(TankColor.BLACK));

    }

    public static final class COLORED_ITEMS {

        public static final ColoredItem TANK  = new ColoredItem(ITEM.TANK, ColorHandler.getIntegerColorFromState(TankColor.GRAY));
        public static final ColoredItem TANK_WHITE = new ColoredItem(ITEM.TANK_WHITE, ColorHandler.getIntegerColorFromState(TankColor.WHITE));
        public static final ColoredItem TANK_ORANGE = new ColoredItem(ITEM.TANK_ORANGE, ColorHandler.getIntegerColorFromState(TankColor.ORANGE));
        public static final ColoredItem TANK_MAGENTA = new ColoredItem(ITEM.TANK_MAGENTA, ColorHandler.getIntegerColorFromState(TankColor.MAGENTA));
        public static final ColoredItem TANK_LIGHT_BLUE = new ColoredItem(ITEM.TANK_LIGHT_BLUE, ColorHandler.getIntegerColorFromState(TankColor.LIGHT_BLUE));
        public static final ColoredItem TANK_YELLOW = new ColoredItem(ITEM.TANK_YELLOW, ColorHandler.getIntegerColorFromState(TankColor.YELLOW));
        public static final ColoredItem TANK_LIME  = new ColoredItem(ITEM.TANK_LIME, ColorHandler.getIntegerColorFromState(TankColor.LIME));
        public static final ColoredItem TANK_PINK  = new ColoredItem(ITEM.TANK_PINK, ColorHandler.getIntegerColorFromState(TankColor.PINK));
        public static final ColoredItem TANK_LIGHT_GRAY = new ColoredItem(ITEM.TANK_LIGHT_GRAY, ColorHandler.getIntegerColorFromState(TankColor.LIGHT_GRAY));
        public static final ColoredItem TANK_CYAN  = new ColoredItem(ITEM.TANK_CYAN, ColorHandler.getIntegerColorFromState(TankColor.CYAN));
        public static final ColoredItem TANK_PURPLE = new ColoredItem(ITEM.TANK_PURPLE, ColorHandler.getIntegerColorFromState(TankColor.PURPLE));
        public static final ColoredItem TANK_BLUE  = new ColoredItem(ITEM.TANK_BLUE, ColorHandler.getIntegerColorFromState(TankColor.BLUE));
        public static final ColoredItem TANK_BROWN = new ColoredItem(ITEM.TANK_BROWN, ColorHandler.getIntegerColorFromState(TankColor.BROWN));
        public static final ColoredItem TANK_GREEN = new ColoredItem(ITEM.TANK_GREEN, ColorHandler.getIntegerColorFromState(TankColor.GREEN));
        public static final ColoredItem TANK_RED = new ColoredItem(ITEM.TANK_RED, ColorHandler.getIntegerColorFromState(TankColor.RED));
        public static final ColoredItem TANK_BLACK = new ColoredItem(ITEM.TANK_BLACK, ColorHandler.getIntegerColorFromState(TankColor.BLACK));

    }

}
