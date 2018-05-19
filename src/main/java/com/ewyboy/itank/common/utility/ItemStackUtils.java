package com.ewyboy.itank.common.utility;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ItemStackUtils {

    public static NBTTagCompound prepareDataTag (ItemStack stack) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }

    public static void dropStackInWorld (World world, BlockPos pos, ItemStack stack) {
        if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
            final float offset = 0.7F;
            final double offX = world.rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
            final double offY = world.rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
            final double offZ = world.rand.nextFloat() * offset + (1.0F - offset) * 0.5D;
            final EntityItem entityitem = new EntityItem(world, pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ, stack);
            entityitem.setPickupDelay(10);
            world.spawnEntity(entityitem);
        }
    }

    public static ItemStack createStackFromTileEntity (TileEntity tile) {
        final ItemStack stack = new ItemStack(tile.getBlockType(), 1);
        prepareDataTag(stack);
        final NBTTagCompound tileTag = tile.writeToNBT(new NBTTagCompound());
        stack.getTagCompound().setTag("TileData", tileTag);
        return stack;
    }

}