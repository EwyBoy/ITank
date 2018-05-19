package com.ewyboy.itank.common.regsiter;

import com.ewyboy.itank.common.blocks.BlockTank;
import com.ewyboy.itank.common.tiles.TileEntityTank;

/**
 * Created by EwyBoy
 */
public class Register {

    public static class Blocks {
        public static final BlockTank tank = new BlockTank();
    }

    public static class Tiles {
        public static TileEntityTank tileTank = new TileEntityTank();
    }
}
