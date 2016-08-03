package com.ewyboy.itank.common.loaders;

import com.ewyboy.itank.common.blocks.BlockTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLoader {

    public static BlockTank tank;

    public static void loadBlocks() {
        tank = new BlockTank();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        tank.registerBlockRenderer();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        tank.registerBlockItemRenderer();
    }
}
