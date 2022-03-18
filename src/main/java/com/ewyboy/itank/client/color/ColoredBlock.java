package com.ewyboy.itank.client.color;

import com.ewyboy.itank.client.interfaces.IBlockColorizer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ColoredBlock implements IBlockColorizer {

    private final int colorIndex;
    private final Block block;

    public ColoredBlock(Block block, int tintIndex) {
        this.block = block;
        this.colorIndex = tintIndex;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public Block blockToColor() {
        return getBlock();
    }

    @Override
    public int getColor(BlockState blockState, @Nullable BlockAndTintGetter iBlockDisplayReader, @Nullable BlockPos blockPos, int tint) {
        return getColorIndex();
    }
}
