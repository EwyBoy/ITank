package com.ewyboy.itank.common.tiles;

import com.ewyboy.itank.common.loaders.ConfigLoader;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Created by EwyBoy
 **/
public class TileEntityTank extends TileEntityBase implements ITickable {

    public TileTank tank;

    public TileEntityTank() {
        this.tank = new TileTank(Fluid.BUCKET_VOLUME * (ConfigLoader.maxTankCapacity / 1000));
        this.tank.setTileEntity(this);
    }

    @Override
    public void writeNBT (NBTTagCompound dataTag) {
        if (this.tank != null) {
            if (this.tank.getFluid() != null) {
                final NBTTagCompound tankTag = new NBTTagCompound();
                this.tank.getFluid().writeToNBT(tankTag);
                dataTag.setTag("FluidData", tankTag);
            }
        }
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {
        if (dataTag.hasKey("FluidData")) {
            this.tank = dataTag.hasKey("FluidData")
                    ? new TileTank(FluidStack.loadFluidStackFromNBT(dataTag.getCompoundTag("FluidData")), Fluid.BUCKET_VOLUME * (ConfigLoader.maxTankCapacity / 1000))
                    : new TileTank(Fluid.BUCKET_VOLUME * (ConfigLoader.maxTankCapacity / 1000));
        }
        this.tank.setTileEntity(this);
    }

    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {
        return capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {
        if (capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
            return (T)this.tank;
        }
        return super.getCapability(capability, facing);
    }

    /** @author shadowfacts Auto Drain*/
    @Override
    public void update() {
        if (tank.getFluid() != null && tank.getFluidAmount() > 0) {
            TileEntity te = world.getTileEntity(pos.down());
            if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP)) {
                IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
                tank.drain(fluidHandler.fill(tank.drain(tank.getCapacity(), false), true), true);
            }
        }
    }

    public TileTank getTank() {
        return tank;
    }
}
