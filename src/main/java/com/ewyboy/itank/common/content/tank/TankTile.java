package com.ewyboy.itank.common.content.tank;

import com.ewyboy.bibliotheca.util.ModLogger;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.config.ConfigOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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

public class TankTile extends BlockEntity {

    private FluidTank tank;
    private CompoundTag updateTag;
    public static int capacity = ConfigOptions.Tanks.tankCapacity;
    private final String compoundKey = "FluidCap";
    private final LazyOptional<IFluidHandler> handler = LazyOptional.of(() -> tank);

    public TankTile(BlockPos pos, BlockState state) {
        super(Register.TILE.TANK, pos, state);

        capacity = ConfigOptions.Tanks.tankCapacity;
        updateTag = getTileData();

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
                TankTile.this.setChanged();
                TankTile.this.clientSync();
            }
        };
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TankTile tile) {
        if (tile.tank.getFluidAmount() > 0) {
            TankTile tank_below = null;

            if (level != null) {
                if (level.getBlockEntity(pos.below()) instanceof TankTile) {
                    tank_below = (TankTile) level.getBlockEntity(pos.below());
                }
            }

            // Fluid always drain to the bottom tank
            if (tank_below != null && tank_below.getBlockState().getValue(TankBlock.COLOR) == state.getValue(TankBlock.COLOR)) {
                tank_below.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP).ifPresent(
                        fluidHandler -> tile.tank.drain(
                                fluidHandler.fill(
                                        tile.tank.drain(
                                                tile.tank.getCapacity(), IFluidHandler.FluidAction.SIMULATE
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
        Stream<ServerPlayer> entities = world.getChunkSource().chunkMap.getPlayers(new ChunkPos(this.worldPosition), false).stream();
        ClientboundBlockEntityDataPacket updatePacket = this.getUpdatePacket();
        entities.forEach(e -> {
            if (updatePacket != null) {
                e.connection.send(updatePacket);
            }
        });
    }


    @Override
    public CompoundTag getUpdateTag() {
        this.saveAdditional(updateTag);
        return updateTag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        tank.readFromNBT(nbt);
        tank.setCapacity(nbt.getInt(compoundKey));
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        saveAdditional(tag);
        return super.save(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        tank.writeToNBT(nbt);
        nbt.putInt(compoundKey, tank.getCapacity());
        updateTag = nbt;
    }


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
