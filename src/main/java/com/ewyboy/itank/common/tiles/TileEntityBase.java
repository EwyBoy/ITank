package com.ewyboy.itank.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBase extends TileEntity {

    @Override
    public void readFromNBT (NBTTagCompound dataTag) {
        this.readNBT(dataTag);
        super.readFromNBT(dataTag);
    }

    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound dataTag) {
        this.writeNBT(dataTag);
        return super.writeToNBT(dataTag);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket (NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        this.readNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag () {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public boolean shouldRefresh (World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    public void writeNBT (NBTTagCompound dataTag) {}

    public void readNBT (NBTTagCompound dataTag) {}
}