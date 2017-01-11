package com.ewyboy.itank.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Created by EwyBoy
 **/
public class TileEntityTank extends TileEntityBase implements ITickable {

    protected TileTank tank;

    public TileEntityTank() {
        this.tank = new TileTank(TileTank.MAX, this);
    }

    @Override
    public void writeNBT(NBTTagCompound dataTag) {
        if (getTank() != null && getTank().getFluid() != null) {
            dataTag.setTag("FluidData", getTank().getFluid().writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readNBT(NBTTagCompound dataTag) {
        this.tank = dataTag.hasKey("FluidData") ?
                new TileTank(FluidStack.loadFluidStackFromNBT(dataTag.getCompoundTag("FluidData")), TileTank.MAX, this) :
                new TileTank(TileTank.MAX, this);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) ? (T) this.tank : super.getCapability(capability, facing);
    }

    /**
     * @author shadowfacts Auto Drain
     */
    @Override
    public void update() {
        if (getTank().getFluid() != null && getTank().getFluidAmount() > 0) {
            TileEntity te = getWorld().getTileEntity(getPos().down());
            if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP)) {
                IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
                getTank().drain(fluidHandler.fill(getTank().drain(getTank().getCapacity(), false), true), true);
            }
        }
    }

    public TileTank getTank() {
        return tank;
    }
}
