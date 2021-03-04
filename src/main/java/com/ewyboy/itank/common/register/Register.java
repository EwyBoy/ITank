package com.ewyboy.itank.common.register;

import com.ewyboy.itank.ITank;
import com.ewyboy.itank.common.content.tank.TankBlock;
import com.ewyboy.itank.common.content.tank.TankTile;
import com.google.common.collect.Sets;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;

public class Register {


    public static final class BLOCK {
        
        public static final TankBlock TANK = new TankBlock(TankBlock.Properties.create(Material.GLASS).notSolid().sound(SoundType.GLASS).hardnessAndResistance(2.0f, 6.0f));
    
    }

    public static final class ITEM {

        public static final BlockNamedItem TANK = new BlockNamedItem(BLOCK.TANK, new Item.Properties().group(ITank.itemGroup));

    }

    public static final class TILE {

        public static final TileEntityType<TankTile> TANK = new TileEntityType<>(TankTile :: new, Sets.newHashSet(BLOCK.TANK), null);

    }

}
