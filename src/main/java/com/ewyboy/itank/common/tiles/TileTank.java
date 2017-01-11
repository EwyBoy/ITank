package com.ewyboy.itank.common.tiles;

import com.ewyboy.itank.common.loaders.ConfigLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileTank extends FluidTank {

    protected static final int MAX = Fluid.BUCKET_VOLUME * (ConfigLoader.maxTankCapacity / 1000);

    public TileTank(int capacity, TileEntityTank tile) {
        this(null, capacity, tile);
    }

    public TileTank(FluidStack fluidStack, int capacity, TileEntityTank tile) {
        super(fluidStack, capacity);
        this.tile = tile;
    }

    public TileTank(Fluid fluid, int amount, int capacity, TileEntityTank tile) {
        this(new FluidStack(fluid, amount), capacity, tile);
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