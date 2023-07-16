package com.ewyboy.itank.common.content.tank;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class BaseTankEntity<T extends BlockEntity> extends Block implements EntityBlock {

    protected abstract BlockEntityType.BlockEntitySupplier<T> getTileSupplier();

    public BaseTankEntity(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected T getTileEntity(Level world, BlockPos pos) {
        return (T) world.getBlockEntity(pos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return getTileSupplier().create(blockPos, blockState);
    }
}