package com.ewyboy.itank.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ItemStacker {

    public static CompoundTag prepareDataTag(ItemStack stack) {
        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        return stack.getTag();
    }

    public static void dropStackInWorld(Level world, BlockPos pos, ItemStack stack) {
        if (!world.isClientSide) {
            final float offset = 0.7F;
            final double offX = world.random.nextFloat() * offset + (1.0F - offset) * 0.5D;
            final double offY = world.random.nextFloat() * offset + (1.0F - offset) * 0.5D;
            final double offZ = world.random.nextFloat() * offset + (1.0F - offset) * 0.5D;
            final ItemEntity itemEntity = new ItemEntity(world, pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ, stack);
            itemEntity.setPickUpDelay(10);
            world.addFreshEntity(itemEntity);
        }
    }

    public static ItemStack createStackFromTileEntity(BlockEntity tile) {
        final ItemStack stack = new ItemStack(tile.getBlockState().getBlock(), 1);
        prepareDataTag(stack);
        final CompoundTag tileTag = tile.saveWithFullMetadata();
        if (stack.getTag() != null) {
            stack.getTag().put("TileData", tileTag);
            stack.getTag().getCompound("TileData").remove("x");
            stack.getTag().getCompound("TileData").remove("y");
            stack.getTag().getCompound("TileData").remove("z");
        }
        return stack;
    }

}
