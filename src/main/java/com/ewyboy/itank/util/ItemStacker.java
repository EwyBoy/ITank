package com.ewyboy.itank.util;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemStacker {

    public static CompoundNBT prepareDataTag(ItemStack stack) {
        if (!stack.hasTag()) stack.setTag(new CompoundNBT());
        return stack.getTag();
    }

    public static void dropStackInWorld(World world, BlockPos pos, ItemStack stack) {
        if (!world.isRemote) {
            final float offset = 0.7F;
            final double offX = world.rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
            final double offY = world.rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
            final double offZ = world.rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
            final ItemEntity itemEntity = new ItemEntity(world, pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ, stack);
            itemEntity.setPickupDelay(10);
            world.addEntity(itemEntity);
        }
    }

    public static ItemStack createStackFromTileEntity(TileEntity tile) {
        final ItemStack stack = new ItemStack(tile.getBlockState().getBlock(), 1);
        prepareDataTag(stack);
        final CompoundNBT tileTag = tile.write(new CompoundNBT());
        if (stack.getTag() != null) {
            stack.getTag().put("TileData", tileTag);
            stack.getTag().getCompound("TileData").remove("x");
            stack.getTag().getCompound("TileData").remove("y");
            stack.getTag().getCompound("TileData").remove("z");
        }
        return stack;
    }

}
