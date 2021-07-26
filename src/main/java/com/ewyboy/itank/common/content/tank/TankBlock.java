package com.ewyboy.itank.common.content.tank;

import com.ewyboy.bibliotheca.client.interfaces.IHasRenderType;
import com.ewyboy.bibliotheca.client.interfaces.IHasSpecialRenderer;
import com.ewyboy.bibliotheca.common.content.block.BaseTileBlock;
import com.ewyboy.bibliotheca.common.helpers.TextHelper;
import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.bibliotheca.util.ItemStacker;
import com.ewyboy.itank.client.TankRenderer;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.common.states.TankColor;
import com.ewyboy.itank.common.states.TankState;
import com.ewyboy.itank.common.states.TankStateProperties;
import com.ewyboy.itank.config.ConfigOptions;
import com.ewyboy.itank.util.ColorHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.Objects;

public class TankBlock extends BaseTileBlock<TankTile> implements IHasRenderType, IHasSpecialRenderer, ContentLoader.IHasNoBlockItem {

    public static final EnumProperty<TankState> STATE = TankStateProperties.TANK_STATE;
    public static final EnumProperty<TankColor> COLOR = TankStateProperties.TANK_COLOR;

    public TankBlock(TankColor color) {
        super(TankBlock.Properties.of(Material.GLASS).noOcclusion().sound(SoundType.GLASS).strength(2.0f, 6.0f));
        registerDefaultState(this.defaultBlockState().setValue(STATE, TankState.ONE).setValue(COLOR, color));
    }

    @Override
    public MaterialColor defaultMaterialColor() {
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
    public ActionResultType use(BlockState state, L world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack held = player.getItemInHand(hand);

        if (FluidUtil.interactWithFluidHandler(player, hand, world, pos, hit.getDirection()) || held.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
            return ActionResultType.SUCCESS;
        }

        // Smart/Easy - Tank building
        if (!world.isClientSide) {
            player.getItemInHand(hand);
            if (player.getItemInHand(hand).getItem() instanceof TankItem) {
                ItemStack stack = player.getItemInHand(hand);
                TankItem item = (TankItem) player.getItemInHand(hand).getItem();
                if (item.getBlock().defaultBlockState().getValue(COLOR) == world.getBlockState(pos).getValue(COLOR)) {
                    for (int i = 0; i < pos.getY() + 4; i++) {
                        if (world.isEmptyBlock(pos.offset(0, i, 0))) {
                            world.setBlock(pos.above(i), this.defaultBlockState(), 3);
                            setRetainedFluidInTank(world, pos.above(i), state, stack);
                            if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
                            world.playSound(player, pos, SoundEvents.GLASS_PLACE, SoundCategory.BLOCKS, 0.0f, 0.0f);
                            break;
                        } else if (!world.isEmptyBlock(pos.above(i)) && world.getBlockState(pos.above(i)).getBlock() != this) {
                            break;
                        }
                    }
                }
                // Dye tanks
            } else if (player.getItemInHand(hand).getItem() instanceof DyeItem) {
                DyeItem dye = (DyeItem) player.getItemInHand(hand).getItem();
                switch(dye.getDyeColor()) {
                    case GRAY: default: setTankColor(world, pos, TankColor.GRAY); break;
                    case WHITE: setTankColor(world, pos, TankColor.WHITE); break;
                    case ORANGE: setTankColor(world, pos, TankColor.ORANGE); break;
                    case MAGENTA: setTankColor(world, pos, TankColor.MAGENTA); break;
                    case LIGHT_BLUE: setTankColor(world, pos, TankColor.LIGHT_BLUE); break;
                    case YELLOW: setTankColor(world, pos, TankColor.YELLOW) ;break;
                    case LIME: setTankColor(world, pos, TankColor.LIME) ;break;
                    case PINK: setTankColor(world, pos, TankColor.PINK) ; break;
                    case LIGHT_GRAY: setTankColor(world, pos, TankColor.LIGHT_GRAY); break;
                    case CYAN: setTankColor(world, pos, TankColor.CYAN); break;
                    case PURPLE: setTankColor(world, pos, TankColor.PURPLE); break;
                    case BLUE: setTankColor(world, pos, TankColor.BLUE); break;
                    case BROWN: setTankColor(world, pos, TankColor.BROWN); break;
                    case GREEN: setTankColor(world, pos, TankColor.GREEN); break;
                    case RED: setTankColor(world, pos, TankColor.RED); break;
                    case BLACK: setTankColor(world, pos, TankColor.BLACK); break;
                }
                world.playSound(player, pos, SoundEvents.SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 0.0f, 0.0f);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        if (ConfigOptions.Tanks.retainFluidAfterExplosions) {
            ItemStacker.dropStackInWorld(world, pos, ItemStacker.createStackFromTileEntity(Objects.requireNonNull(world.getBlockEntity(pos))));
            world.destroyBlock(pos, false);
        } else {
            ItemStacker.dropStackInWorld(world, pos, new ItemStack(this));
        }
        this.wasExploded(world, pos, explosion);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        final TankTile tank = (TankTile) world.getBlockEntity(pos);
        return tank != null ? ItemStacker.createStackFromTileEntity(tank) : super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        return false;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (!player.isCreative()) {
            if (player.isShiftKeyDown()) {
                ItemStacker.dropStackInWorld(world, pos, new ItemStack(this));
            } else {
                final TankTile tank = (TankTile) world.getBlockEntity(pos);
                if (tank != null) {
                    ItemStacker.dropStackInWorld(world, pos, tank.getFluid().isEmpty() ? new ItemStack(this) : ItemStacker.createStackFromTileEntity(Objects.requireNonNull(tank)));
                }
            }
            world.playSound(player, pos, SoundEvents.GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 0.0f);
            world.removeBlock(pos, false);
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
       setRetainedFluidInTank(world, pos, state, stack);
    }

    private void setRetainedFluidInTank(World world, BlockPos pos, BlockState state, ItemStack stack) {
        if (stack.hasTag()) {
            final TankTile tank = (TankTile) world.getBlockEntity(pos);
            if (tank != null) {
                if (stack.getTag() != null) {
                    CompoundNBT tag = stack.getTag().getCompound("TileData");
                    tag.putInt("x", pos.getX());
                    tag.putInt("y", pos.getY());
                    tag.putInt("z", pos.getZ());
                    tank.load(state, tag);
                }
            }
        }
    }

    private void setTankState(World world, BlockPos pos, TankState state) {
        world.setBlockAndUpdate(pos, defaultBlockState().setValue(STATE, state));
    }

    private void setTankColor(World world, BlockPos pos, TankColor color) {
        if (!world.getBlockState(pos).getValue(COLOR).equals(color)) {
            TankBlock tank = ColorHandler.getBlockColorFromState(color);
            final TankTile oldTile = (TankTile) world.getBlockEntity(pos);
            CompoundNBT tag;
            if (oldTile != null) {
                tag = oldTile.save(new CompoundNBT());
                world.setBlockAndUpdate(pos, tank.defaultBlockState().setValue(COLOR, color));
                final TankTile newTile = (TankTile) world.getBlockEntity(pos);
                if (newTile != null) {
                    newTile.load(world.getBlockState(pos), tag);
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
        ClientRegistry.bindTileEntityRenderer(Register.TILE.TANK, TankRenderer :: new);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE).add(COLOR);
    }

    @Override
    protected Class<TankTile> getTileClass() {
        return TankTile.class;
    }

    public TankTile getTank(IBlockReader world, BlockPos pos) {
        return (TankTile) world.getBlockEntity(pos);
    }

    private void colorFluidName(String fluidName, List<ITextComponent> tooltip) {
        if(!Objects.equals(fluidName, "Air")) {
            switch(fluidName) {
                case "Water": tooltip.add(new StringTextComponent("Fluid: " + TextFormatting.AQUA + fluidName)); break;
                case "Lava": tooltip.add(new StringTextComponent("Fluid: " + TextFormatting.RED + fluidName)); break;
                default: tooltip.add(new StringTextComponent("Fluid: " + TextFormatting.GREEN + fluidName)); break;
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        FluidStack fluid = null;
        if (stack.getTag() != null && stack.hasTag() && stack.getTag().contains("TileData")) {
            fluid = FluidStack.loadFluidStackFromNBT(stack.getTag().getCompound("TileData"));
        }
        if (fluid != null) {
            String fluidName = fluid.getDisplayName().getString();
            colorFluidName(fluidName, tooltip);
            tooltip.add(new StringTextComponent(TextHelper.formatCapacityInfo(fluid.getAmount(), ConfigOptions.Tanks.tankCapacity, "mB")));
        }
    }

}
