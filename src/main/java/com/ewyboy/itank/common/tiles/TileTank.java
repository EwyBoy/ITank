package com.ewyboy.itank.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileTank extends TileEntityBase implements ITickable {

    public FluidTank tank = new FluidTank(Fluid.BUCKET_VOLUME * 8);
    private int prevAmount = tank.getFluidAmount();

    public TileTank() {}

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        nbt.getString("fluid");
        nbt.getInteger("liquid");
        tank.setFluid(new FluidStack(FluidRegistry.getFluid(nbt.getString("fluid")), nbt.getInteger("liquid")));

        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (tank.getFluid() != null && tank.getFluidAmount() != 0) {
            nbt.setString("fluid", tank.getFluid().getFluid().getName());
            nbt.setInteger("liquid", tank.getFluidAmount());
        }
        return super.writeToNBT(nbt);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY))
            return (T)tank;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY))
            return true;
        return super.hasCapability(capability, facing);
    }

    /** @author shadowfacts Auto Drain*/
    @Override
    public void update() {
        if (tank.getFluid() != null && tank.getFluidAmount() > 0) {
            TileEntity te = worldObj.getTileEntity(pos.down());
            if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP)) {
                IFluidHandler fluidHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
                tank.drain(fluidHandler.fill(tank.drain(tank.getCapacity(), false), true), true);
            }
        }
    }
}