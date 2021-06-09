package com.ewyboy.itank.common.content.tank;

import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.config.Config;
import com.ewyboy.itank.config.ConfigOptions;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

public class TankTile extends TileEntity implements ITickableTileEntity {

    private FluidTank tank;
    public static int capacity = ConfigOptions.Tanks.tankCapacity;
    private final String compoundKey = "FluidCap";
    private final LazyOptional<IFluidHandler> handler = LazyOptional.of(() -> tank);

    public TankTile() {
        super(Register.TILE.TANK);

        capacity = ConfigOptions.Tanks.tankCapacity;

        this.tank = new FluidTank(capacity) {

            @Override
            public int fill(FluidStack resource, FluidAction action) {
                TileEntity tankBelow = null;
                if(world != null) {
                    if (world.getTileEntity(pos.down()) instanceof TankTile) {
                        tankBelow = world.getTileEntity(pos.down());
                    }
                }
                // Fills bottom tank
                if(tankBelow instanceof TankTile) {
                    int tankFill = ((TankTile) tankBelow).tank.fill(resource, action);
                    return tankFill != 0 ? tankFill : super.fill(resource, action);
                }
                return super.fill(resource, action);
            }

            @Override
            protected void onContentsChanged() {
                TankTile.this.clientSync();
            }
        };

    }

    @Override
    public void tick() {
        if(tank.getFluidAmount() > 0) {
            TankTile tank_below = null;

            if(world != null) {
                if (world.getTileEntity(pos.down()) instanceof TankTile) {
                    tank_below = (TankTile) world.getTileEntity(pos.down());
                }
            }

            if(tank_below != null) {
                // Fluid always drain to the bottom tank
                tank_below.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP).ifPresent(
                        fluidHandler -> tank.drain(
                                fluidHandler.fill(
                                        tank.drain(
                                                tank.getCapacity(), IFluidHandler.FluidAction.SIMULATE
                                        ), IFluidHandler.FluidAction.EXECUTE
                                ), IFluidHandler.FluidAction.EXECUTE
                        )
                );
            }
        }
    }

    // Data Handling Section

    public void clientSync() {
        if(Objects.requireNonNull(this.getWorld()).isRemote) {
            return;
        }
        ServerWorld world = (ServerWorld) this.getWorld();
        Stream<ServerPlayerEntity> entities = world.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(this.pos), false);
        SUpdateTileEntityPacket updatePacket = this.getUpdatePacket();
        entities.forEach(e -> {
            if(updatePacket != null) {
                e.connection.sendPacket(updatePacket);
            }
        });
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(world.getBlockState(getPos()), pkt.getNbtCompound());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        tank.readFromNBT(nbt);
        tank.setCapacity(nbt.getInt(compoundKey));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt = super.write(nbt);
        tank.writeToNBT(nbt);
        nbt.putInt(compoundKey, tank.getCapacity());

        return nbt;
    }


    // Liquid & Fluid Handling Section

    public FluidStack getFluid() {
        return this.tank.getFluid();
    }

    public FluidTank getTank() {
        return this.tank;
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(capability, facing);
    }


}
