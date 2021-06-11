package com.ewyboy.itank.common.content.tank;

import com.ewyboy.bibliotheca.client.interfaces.IHasRenderType;
import com.ewyboy.bibliotheca.client.interfaces.IHasSpecialRenderer;
import com.ewyboy.bibliotheca.common.content.block.BaseTileBlock;
import com.ewyboy.bibliotheca.common.helpers.TextHelper;
import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaInfo;
import com.ewyboy.bibliotheca.util.ItemStacker;
import com.ewyboy.itank.client.TankRenderer;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.common.states.TankColor;
import com.ewyboy.itank.common.states.TankState;
import com.ewyboy.itank.common.states.TankStateProperties;
import com.ewyboy.itank.config.ConfigOptions;
import com.ewyboy.itank.util.ColorHandler;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class TankBlock extends BaseTileBlock<TankTile> implements IHasRenderType, IHasSpecialRenderer, ContentLoader.IHasNoBlockItem, IWailaInfo {

    public static final EnumProperty<TankState> STATE = TankStateProperties.TANK_STATE;
    public static final EnumProperty<TankColor> COLOR = TankStateProperties.TANK_COLOR;

    public TankBlock(TankColor color) {
        super(TankBlock.Properties.create(Material.GLASS).notSolid().sound(SoundType.GLASS).hardnessAndResistance(2.0f, 6.0f));
        setDefaultState(this.getDefaultState().with(STATE, TankState.ONE).with(COLOR, color));
    }

    @Override
    public MaterialColor getMaterialColor() {
        return ColorHandler.getMaterialColorFromState(getDefaultState().get(COLOR));
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (world.getBlockState(pos.up()).getBlock() == this && world.getBlockState(pos.down()).getBlock() == this) {
            setTankState(world, pos, TankState.MID);
        } else if (world.getBlockState(pos.up()).getBlock() == this && world.getBlockState(pos.down()).getBlock() != this) {
            setTankState(world, pos, TankState.BOT);
        } else if (world.getBlockState(pos.up()).getBlock() != this && world.getBlockState(pos.down()).getBlock() == this) {
            setTankState(world, pos, TankState.TOP);
        } else {
            setTankState(world, pos, TankState.ONE);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack held = player.getHeldItem(hand);

        if (FluidUtil.interactWithFluidHandler(player, hand, world, pos, hit.getFace()) || held.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
            return ActionResultType.SUCCESS;
        }

        // Smart/Easy - Tank building
        if (!world.isRemote) {
            player.getHeldItem(hand);
            if (player.getHeldItem(hand).getItem() instanceof TankItem) {
                ItemStack stack = player.getHeldItem(hand);
                TankItem item = (TankItem) player.getHeldItem(hand).getItem();
                if (item.getBlock().getDefaultState().get(COLOR) == world.getBlockState(pos).get(COLOR)) {
                    for (int i = 0; i < pos.getY() + 4; i++) {
                        if (world.isAirBlock(pos.add(0, i, 0))) {
                            world.setBlockState(pos.up(i), this.getDefaultState(), 3);
                            setRetainedFluidInTank(world, pos.up(i), state, stack);
                            if (!player.isCreative()) player.getHeldItem(hand).shrink(1);
                            world.playSound(player, pos, SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 0.0f, 0.0f);
                            break;
                        } else if (!world.isAirBlock(pos.up(i)) && world.getBlockState(pos.up(i)).getBlock() != this) {
                            break;
                        }
                    }
                }
                // Dye tanks
            } else if (player.getHeldItem(hand).getItem() instanceof DyeItem) {
                DyeItem dye = (DyeItem) player.getHeldItem(hand).getItem();
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
                world.playSound(player, pos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 0.0f, 0.0f);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        if (ConfigOptions.Tanks.retainFluidAfterExplosions) {
            ItemStacker.dropStackInWorld(world, pos, ItemStacker.createStackFromTileEntity(Objects.requireNonNull(world.getTileEntity(pos))));
            world.destroyBlock(pos, false);
        } else {
            ItemStacker.dropStackInWorld(world, pos, new ItemStack(this));
        }
        this.onExplosionDestroy(world, pos, explosion);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        final TankTile tank = (TankTile) world.getTileEntity(pos);
        return tank != null ? ItemStacker.createStackFromTileEntity(tank) : super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
        return false;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (!player.isCreative()) {
            if (player.isSneaking()) {
                ItemStacker.dropStackInWorld(world, pos, new ItemStack(this));
            } else {
                final TankTile tank = (TankTile) world.getTileEntity(pos);
                if (tank != null) {
                    ItemStacker.dropStackInWorld(world, pos, tank.getFluid().isEmpty() ? new ItemStack(this) : ItemStacker.createStackFromTileEntity(Objects.requireNonNull(tank)));
                }
            }
            world.playSound(player, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0f, 0.0f);
            world.removeBlock(pos, false);
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
       setRetainedFluidInTank(world, pos, state, stack);
    }

    private void setRetainedFluidInTank(World world, BlockPos pos, BlockState state, ItemStack stack) {
        if (stack.hasTag()) {
            final TankTile tank = (TankTile) world.getTileEntity(pos);
            if (tank != null) {
                if (stack.getTag() != null) {
                    CompoundNBT tag = stack.getTag().getCompound("TileData");
                    tag.putInt("x", pos.getX());
                    tag.putInt("y", pos.getY());
                    tag.putInt("z", pos.getZ());
                    tank.read(state, tag);
                }
            }
        }
    }

    private void setTankState(World world, BlockPos pos, TankState state) {
        world.setBlockState(pos, getDefaultState().with(STATE, state));
    }

    private void setTankColor(World world, BlockPos pos, TankColor color) {
        if (!world.getBlockState(pos).get(COLOR).equals(color)) {
            TankBlock tank = ColorHandler.getBlockColorFromState(color);
            final TankTile oldTile = (TankTile) world.getTileEntity(pos);
            CompoundNBT tag;
            if (oldTile != null) {
                tag = oldTile.write(new CompoundNBT());
                world.setBlockState(pos, tank.getDefaultState().with(COLOR, color));
                final TankTile newTile = (TankTile) world.getTileEntity(pos);
                if (newTile != null) {
                    newTile.read(world.getBlockState(pos), tag);
                }
            }
        }
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutoutMipped();
    }

    @Override
    public void initSpecialRenderer() {
        ClientRegistry.bindTileEntityRenderer(Register.TILE.TANK, TankRenderer :: new);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE).add(COLOR);
    }

    @Override
    protected Class<TankTile> getTileClass() {
        return TankTile.class;
    }

    public TankTile getTank(IBlockReader world, BlockPos pos) {
        return (TankTile) world.getTileEntity(pos);
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
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
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

    @Override
    public void getWailaBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        TankTile tank = getTank(accessor.getWorld(), accessor.getPosition());
        if (tank != null) {
            tooltip.add(new StringTextComponent(TextHelper.formatCapacityInfo(tank.getTank().getFluidAmount(), tank.getTank().getCapacity(), "mB")));
            if(tank.getFluid().getFluid() != null) {
                String fluidName = Objects.requireNonNull(tank.getTank().getFluid().getDisplayName().getString());
                colorFluidName(fluidName, tooltip);
            }
        }
    }

}
