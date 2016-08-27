package com.ewyboy.itank.common.tiles;

import com.ewyboy.itank.common.nbt.INBTConverter;
import com.ewyboy.itank.common.nbt.NBT;
import com.ewyboy.itank.common.utility.helpers.TileHelper;
import com.ewyboy.itank.common.utility.interfaces.IOrientable;
import com.ewyboy.itank.common.utility.interfaces.IRotatable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

import java.util.Arrays;

public class TileEntityBase extends TileEntity implements /*IWailaHeadMessage,*/ IOrientable, IRotatable {

    private String customName;
    private int renderedFragment = 0;
    private NBTTagCompound machineItemData;
    private EnumFacing forward = EnumFacing.NORTH;

    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        writeToNBT(data);
        initMachineData();
        return new SPacketUpdateTileEntity(this.pos, 1, data);
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity) {
        readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
        worldObj.markBlockRangeForRenderUpdate(this.pos, this.pos);
        markForUpdate();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        Arrays.stream(getClass().getFields()).filter(f -> f.isAnnotationPresent(NBT.class)).forEach(f -> {
            try {
                NBT nbt = f.getAnnotation(NBT.class);
                INBTConverter converter = nbt.value().getConverter().newInstance().setKey(f.getName());
                f.set(this, converter.convert(compound));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        Arrays.stream(getClass().getFields()).filter(f -> f.isAnnotationPresent(NBT.class)).forEach(f -> {
            try {
                NBT nbt = f.getAnnotation(NBT.class);
                INBTConverter converter = nbt.value().getConverter().newInstance().setKey(f.getName());
                converter.convert(compound, f.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });
        return compound;
    }

    public void initMachineData() {}

    public void markForUpdate() {
        if (this.renderedFragment > 0) {
            this.renderedFragment |= 0x1;
        } else if (this.worldObj != null) {
            Block block = worldObj.getBlockState(this.pos).getBlock();
            //todo: look at this, is it correct?
            this.worldObj.notifyBlockUpdate(this.pos, worldObj.getBlockState(this.pos), worldObj.getBlockState(this.pos), 3);

            int xCoord = this.pos.getX();
            int yCoord = this.pos.getY();
            int zCoord = this.pos.getZ();

            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord + 1, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord - 1, yCoord, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord + 1, yCoord, zCoord), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord - 1), block);
            this.worldObj.notifyBlockOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord + 1), block);
        }
    }

    public void markForLightUpdate() {
        if (this.worldObj.isRemote) {
            this.worldObj.notifyBlockUpdate(this.pos, worldObj.getBlockState(this.pos), worldObj.getBlockState(this.pos), 3);
        }

        this.worldObj.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
    }

    public void onChunkLoad() {
        if (this.isInvalid())
            this.validate();

        markForUpdate();
    }

    @Override
    public void onChunkUnload() {
        if (!this.isInvalid())
            this.invalidate();
    }

    public TileEntity getTile() {
        return this;
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public boolean hasCustomName() {
        return (this.customName != null) && (this.customName.length() > 0);
    }

    public String getUnlocalizedName() {
        Item item = Item.getItemFromBlock(worldObj.getBlockState(this.pos).getBlock());
        ItemStack itemStack = new ItemStack(item, 1, getBlockMetadata());

        return itemStack.getUnlocalizedName() + ".name";
    }

    public void setName(String name) {
        this.customName = name;
    }

    public NBTTagCompound getMachineItemData() {
        return machineItemData;
    }

    public void setMachineItemData(NBTTagCompound machineItemData) {
        this.machineItemData = machineItemData;
    }

    public IBlockState getBlockState() {
        if (worldObj == null)
            return null;

        return worldObj.getBlockState(pos);
    }

    @Override
    public boolean canBeRotated() {
        return false;
    }

    @Override
    public EnumFacing getForward() {
        return forward;
    }

    @Override
    public void setOrientation(EnumFacing forward) {
        this.forward = forward;
        markDirty();
        markForUpdate();
    }

    public void dropItems() {
        TileHelper.DropItems(this);
    }

    @Override
    public EnumFacing getDirection() {
        return getForward();
    }
}
