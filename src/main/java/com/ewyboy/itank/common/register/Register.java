package com.ewyboy.itank.common.register;

import com.ewyboy.itank.ITank;
import com.ewyboy.itank.common.content.tank.TankBlock;
import com.ewyboy.itank.common.content.tank.TankItem;
import com.ewyboy.itank.common.content.tank.TankTile;
import com.ewyboy.itank.common.states.TankColor;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;

import java.util.HashSet;
import java.util.Set;

public class Register {

    public static final class BLOCK {
        
        public static final TankBlock TANK = new TankBlock();
        //public static final TankBlock TANK_BLUE = new TankBlock(TankColor.BLUE);
        //public static final TankBlock TANK_RED = new TankBlock(TankColor.RED);
        //public static final TankBlock TANK_GREEN = new TankBlock(TankColor.GREEN);
        //public static final TankBlock TANK_YELLOW = new TankBlock(TankColor.YELLOW);

    }

    public static final class ITEM {

        public static final TankItem TANK = new TankItem(BLOCK.TANK, new Item.Properties().tab(ITank.itemGroup));
        //public static final TankItem TANK_BLUE = new TankItem(BLOCK.TANK_BLUE, new Item.Properties().group(ITank.itemGroup));
        //public static final TankItem TANK_RED = new TankItem(BLOCK.TANK_RED, new Item.Properties().group(ITank.itemGroup));
        //public static final TankItem TANK_GREEN = new TankItem(BLOCK.TANK_GREEN, new Item.Properties().group(ITank.itemGroup));
        //public static final TankItem TANK_YELLOW = new TankItem(BLOCK.TANK_YELLOW, new Item.Properties().group(ITank.itemGroup));

    }

    private static final Set<Block> TANKS = new HashSet<>();

    static {
        TANKS.add(BLOCK.TANK);
        //TANKS.add(BLOCK.TANK_BLUE);
        //TANKS.add(BLOCK.TANK_RED);
        //TANKS.add(BLOCK.TANK_GREEN);
        //TANKS.add(BLOCK.TANK_YELLOW);
    }

    public static final class TILE {

        public static final TileEntityType<TankTile> TANK = new TileEntityType<>(TankTile :: new, Sets.newHashSet(TANKS), null);

    }

}
