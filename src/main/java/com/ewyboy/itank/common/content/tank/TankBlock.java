package com.ewyboy.itank.common.content.tank;

import com.ewyboy.itank.client.TankRenderer;
import com.ewyboy.itank.client.interfaces.IHasRenderType;
import com.ewyboy.itank.client.interfaces.IHasSpecialRenderer;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.common.states.TankColor;
import com.ewyboy.itank.common.states.TankState;
import com.ewyboy.itank.common.states.TankStateProperties;
import com.ewyboy.itank.config.ConfigOptions;
import com.ewyboy.itank.util.ColorHandler;
import com.ewyboy.itank.util.ItemStacker;
import com.ewyboy.itank.util.TextHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class TankBlock extends BaseTankEntity<TankTile> implements IHasRenderType, IHasSpecialRenderer {

    public static final EnumProperty<TankState> STATE = TankStateProperties.TANK_STATE;
    public static final EnumProperty<TankColor> COLOR = TankStateProperties.TANK_COLOR;

    public TankBlock(TankColor color) {
        super(TankBlock.Properties.of().noOcclusion().sound(SoundType.GLASS).strength(2.0f, 6.0f));
        registerDefaultState(this.defaultBlockState().setValue(STATE, TankState.ONE).setValue(COLOR, color));
    }

    @Override
    public MapColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MapColor defaultColor) {
        return ColorHandler.getMaterialColorFromState(defaultBlockState().getValue(COLOR));
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (world.getBlockState(pos.above()).getBlock() == this && world.getBlockState(pos.below()).getBlock() == this) {
            setTankState(world, pos, TankState.MID);
        } else if (world.getBlockState(pos.above()).getBlock() == this && world.getBlockState(pos.below()).getBlock() != this) {
            setTankState(world, pos, TankState.BOT);
        } else if (world.getBlockState(pos.above()).getBlock() != this && world.getBlockState(pos.below()).getBlock() == this) {
            setTankState(world, pos, TankState.TOP);
        } else {
            setTankState(world, pos, TankState.ONE);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);

        if (FluidUtil.interactWithFluidHandler(player, hand, world, pos, hit.getDirection()) || held.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) {
            return InteractionResult.SUCCESS;
        }

        // Smart/Easy - Tank building
        if (!world.isClientSide) {
            player.getItemInHand(hand);
            if (player.getItemInHand(hand).getItem() instanceof TankItem item) {
                ItemStack stack = player.getItemInHand(hand);
                if (item.getBlock().defaultBlockState().getValue(COLOR) == world.getBlockState(pos).getValue(COLOR)) {
                    for (int i = 0; i < pos.getY() + 4; i++) {
                        if (world.isEmptyBlock(pos.offset(0, i, 0))) {
                            world.setBlock(pos.above(i), this.defaultBlockState(), 3);
                            setRetainedFluidInTank(world, pos.above(i), stack);
                            if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
                            world.playSound(player, pos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 0.0f, 0.0f);
                            break;
                        } else if (!world.isEmptyBlock(pos.above(i)) && world.getBlockState(pos.above(i)).getBlock() != this) {
                            break;
                        }
                    }
                }
                // Dye tanks
            } else if (player.getItemInHand(hand).getItem() instanceof DyeItem dye) {
                switch (dye.getDyeColor()) {
                    case WHITE -> setTankColor(world, pos, TankColor.WHITE);
                    case ORANGE -> setTankColor(world, pos, TankColor.ORANGE);
                    case MAGENTA -> setTankColor(world, pos, TankColor.MAGENTA);
                    case LIGHT_BLUE -> setTankColor(world, pos, TankColor.LIGHT_BLUE);
                    case YELLOW -> setTankColor(world, pos, TankColor.YELLOW);
                    case LIME -> setTankColor(world, pos, TankColor.LIME);
                    case PINK -> setTankColor(world, pos, TankColor.PINK);
                    case LIGHT_GRAY -> setTankColor(world, pos, TankColor.LIGHT_GRAY);
                    case CYAN -> setTankColor(world, pos, TankColor.CYAN);
                    case PURPLE -> setTankColor(world, pos, TankColor.PURPLE);
                    case BLUE -> setTankColor(world, pos, TankColor.BLUE);
                    case BROWN -> setTankColor(world, pos, TankColor.BROWN);
                    case GREEN -> setTankColor(world, pos, TankColor.GREEN);
                    case RED -> setTankColor(world, pos, TankColor.RED);
                    case BLACK -> setTankColor(world, pos, TankColor.BLACK);
                    default -> setTankColor(world, pos, TankColor.GRAY);
                }
                world.playSound(player, pos, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 0.0f, 0.0f);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        if (ConfigOptions.Tanks.retainFluidAfterExplosions) {
            ItemStacker.dropStackInWorld(world, pos, ItemStacker.createStackFromTileEntity(Objects.requireNonNull(world.getBlockEntity(pos))));
            world.destroyBlock(pos, false);
        } else {
            ItemStacker.dropStackInWorld(world, pos, new ItemStack(this));
        }
        this.wasExploded(world, pos, explosion);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        final TankTile tank = (TankTile) world.getBlockEntity(pos);
        return tank != null ? ItemStacker.createStackFromTileEntity(tank) : super.getCloneItemStack(state, target, world, pos, player);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
        return false;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (!player.isCreative()) {
            if (player.isShiftKeyDown()) {
                ItemStacker.dropStackInWorld(level, pos, new ItemStack(this));
            } else {
                final TankTile tank = (TankTile) level.getBlockEntity(pos);
                if (tank != null) {
                    ItemStacker.dropStackInWorld(level, pos, tank.getFluid().isEmpty() ? new ItemStack(this) : ItemStacker.createStackFromTileEntity(Objects.requireNonNull(tank)));
                }
            }
            level.playSound(player, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 0.0f);
            level.removeBlock(pos, false);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
       setRetainedFluidInTank(world, pos, stack);
    }

    private void setRetainedFluidInTank(Level world, BlockPos pos, ItemStack stack) {
        if (stack.hasTag()) {
            final TankTile tank = (TankTile) world.getBlockEntity(pos);
            if (tank != null) {
                if (stack.getTag() != null) {
                    CompoundTag tag = stack.getTag().getCompound("TileData");
                    tag.putInt("x", pos.getX());
                    tag.putInt("y", pos.getY());
                    tag.putInt("z", pos.getZ());
                    tank.load(tag);
                }
            }
        }
    }

    private void setTankState(Level world, BlockPos pos, TankState state) {
        world.setBlockAndUpdate(pos, defaultBlockState().setValue(STATE, state));
    }

    private void setTankColor(Level world, BlockPos pos, TankColor color) {
        if (!world.getBlockState(pos).getValue(COLOR).equals(color)) {
            TankBlock tank = ColorHandler.getBlockColorFromState(color);
            final TankTile oldTile = (TankTile) world.getBlockEntity(pos);
            if (oldTile != null) {
                oldTile.saveAdditional(new CompoundTag());
                world.setBlockAndUpdate(pos, tank.defaultBlockState().setValue(COLOR, color));
                final TankTile newTile = (TankTile) world.getBlockEntity(pos);
                if (newTile != null) {
                    newTile.load(oldTile.saveWithFullMetadata());
                }
            }
        }
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }

    @Override
    public void initSpecialRenderer() {
        BlockEntityRenderers.register(Register.TILE.TANK.get(), TankRenderer :: new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE).add(COLOR);
    }

    public TankTile getTank(BlockGetter world, BlockPos pos) {
        return (TankTile) world.getBlockEntity(pos);
    }

    private void colorFluidName(String fluidName, List<Component> tooltip) {
        if(!Objects.equals(fluidName, "Air")) {
            switch (fluidName) {
                case "Water" -> tooltip.add(Component.literal("Fluid: " + ChatFormatting.AQUA + fluidName));
                case "Lava" -> tooltip.add(Component.literal("Fluid: " + ChatFormatting.RED + fluidName));
                default -> tooltip.add(Component.literal("Fluid: " + ChatFormatting.GREEN + fluidName));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flag) {
        FluidStack fluid = null;
        if (stack.getTag() != null && stack.hasTag() && stack.getTag().contains("TileData")) {
            fluid = FluidStack.loadFluidStackFromNBT(stack.getTag().getCompound("TileData"));
        }
        if (fluid != null) {
            String fluidName = fluid.getDisplayName().getString();
            colorFluidName(fluidName, tooltip);
            tooltip.add(Component.literal(TextHelper.formatCapacityInfo(fluid.getAmount(), ConfigOptions.Tanks.tankCapacity, "mB")));
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> tile) {
        return (level, pos, blockState, tank) -> TankTile.serverTick(level, pos, blockState, (TankTile) tank);
    }

    @Override
    protected BlockEntityType.BlockEntitySupplier<TankTile> getTileSupplier() {
        return TankTile :: new;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return this.getTileSupplier().create(pos, state);
    }
}
