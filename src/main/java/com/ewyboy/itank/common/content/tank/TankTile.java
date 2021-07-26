package com.ewyboy.itank.common.content.tank;

import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.config.Config;
import com.ewyboy.itank.config.ConfigOptions;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.server.level.ServerLevel;
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

public class TankTile extends BlockEntity implements TickableBlockEntity {

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
                BlockEntity tankBelow = null;
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
        ServerLevel world = (ServerLevel) this.getLevel();
        Stream<ServerPlayer> entities = world.getChunkSource().chunkMap.getPlayers(new ChunkPos(this.worldPosition), false);
        ClientboundBlockEntityDataPacket updatePacket = this.getUpdatePacket();
        entities.forEach(e -> {
            if (updatePacket != null) {
                e.connection.send(updatePacket);
            }
        });
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundTag tag) {
        this.load(state, tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(level.getBlockState(getBlockPos()), pkt.getTag());
    }

    @Override
    public void load(BlockState state, CompoundTag nbt) {
        super.load(state, nbt);
        tank.readFromNBT(nbt);
        tank.setCapacity(nbt.getInt(compoundKey));
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
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
