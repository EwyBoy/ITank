package com.ewyboy.itank.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileTank extends FluidTank {

    public TileTank(int capacity) {
        super(capacity);
    }

    public TileTank(FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
    }

    public TileTank(Fluid fluid, int amount, int capacity) {
        super(fluid, amount, capacity);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return super.canFillFluidType(fluid);
    }

    @Override
    public void onContentsChanged() {
        if (this.tile != null) {
            final IBlockState state = this.tile.getWorld().getBlockState(this.tile.getPos());
            this.tile.getWorld().notifyBlockUpdate(this.tile.getPos(), state, state, 8);
            this.tile.markDirty();
        }
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        TileEntity tileDown = tile.getWorld().getTileEntity(tile.getPos().down());
        if (tileDown instanceof TileEntityTank) {
            int tankFill = ((TileEntityTank) tileDown).tank.fill(resource, doFill);
            return tankFill != 0 ? tankFill : super.fill(resource, doFill);
        }
        return super.fill(resource, doFill);
    }
}