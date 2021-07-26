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

import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

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
                if (level != null) {
                    if (level.getBlockEntity(worldPosition.below()) instanceof TankTile) {
                        tankBelow = level.getBlockEntity(worldPosition.below());
                    }
                }
                if (tankBelow != null) {
                    // Fills bottom tank
                    if (tankBelow instanceof TankTile && tankBelow.getBlockState().getValue(TankBlock.COLOR) == getBlockState().getValue(TankBlock.COLOR)) {
                        int tankFill = ((TankTile) tankBelow).tank.fill(resource, action);
                        if (tankFill != 0) {
                            return tankFill;
                        }
                        return super.fill(resource, action);
                    }
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
        if (tank.getFluidAmount() > 0) {
            TankTile tank_below = null;

            if (level != null) {
                if (level.getBlockEntity(worldPosition.below()) instanceof TankTile) {
                    tank_below = (TankTile) level.getBlockEntity(worldPosition.below());
                }
            }

            // Fluid always drain to the bottom tank
            if (tank_below != null && tank_below.getBlockState().getValue(TankBlock.COLOR) == getBlockState().getValue(TankBlock.COLOR)) {
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
        if (Objects.requireNonNull(this.getLevel()).isClientSide) {
            return;
        }
        ServerWorld world = (ServerWorld) this.getLevel();
        Stream<ServerPlayerEntity> entities = world.getChunkSource().chunkMap.getPlayers(new ChunkPos(this.worldPosition), false);
        SUpdateTileEntityPacket updatePacket = this.getUpdatePacket();
        entities.forEach(e -> {
            if (updatePacket != null) {
                e.connection.send(updatePacket);
            }
        });
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.load(state, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(level.getBlockState(getBlockPos()), pkt.getTag());
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        tank.readFromNBT(nbt);
        tank.setCapacity(nbt.getInt(compoundKey));
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt = super.save(nbt);
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
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(capability, facing);
    }


}
