package com.ewyboy.itank.common.content.tank;

import com.ewyboy.bibliotheca.client.interfaces.IHasRenderType;
import com.ewyboy.bibliotheca.client.interfaces.IHasSpecialRenderer;
import com.ewyboy.bibliotheca.common.content.block.BaseTileBlock;
import com.ewyboy.bibliotheca.common.helpers.TextHelper;
import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.bibliotheca.compatibilities.hwyla.IWailaInfo;
import com.ewyboy.itank.client.TankRenderer;
import com.ewyboy.itank.common.register.Register;
import com.ewyboy.itank.common.states.TankColor;
import com.ewyboy.itank.common.states.TankState;
import com.ewyboy.itank.common.states.TankStateProperties;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.List;
import java.util.Objects;

public class TankBlock extends BaseTileBlock<TankTile> implements IHasRenderType, IHasSpecialRenderer, ContentLoader.IHasNoBlockItem, IWailaInfo {

    public int colorIndex;

    public static final EnumProperty<TankState> STATE = TankStateProperties.TANK_STATE;
    public static final EnumProperty<TankColor> COLOR = TankStateProperties.TANK_COLOR;

    public TankBlock() {
        super(TankBlock.Properties.of(Material.GLASS).air().sound(SoundType.GLASS).strength(2.0f, 6.0f));
        registerDefaultState(this.defaultBlockState().setValue(STATE, TankState.ONE));
        //setDefaultState(this.getDefaultState().with(STATE, TankState.ONE).with(COLOR, color));
        //this.colorIndex = ColorHandler.stateColorToIntegerColorMap.get(color);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (world.getBlockState(pos.above()).getBlock() == this && world.getBlockState(pos.below()).getBlock() == this) {
            setState(world, pos, TankState.MID);
        } else if (world.getBlockState(pos.above()).getBlock() == this && world.getBlockState(pos.below()).getBlock() != this) {
            setState(world, pos, TankState.BOT);
        } else if (world.getBlockState(pos.above()).getBlock() != this && world.getBlockState(pos.below()).getBlock() == this) {
            setState(world, pos, TankState.TOP);
        } else {
            setState(world, pos, TankState.ONE);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack held = player.getItemInHand(hand);

        if (FluidUtil.interactWithFluidHandler(player, hand, world, pos, hit.getDirection()) || held.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
            return ActionResultType.SUCCESS;
        }

        // Smart/Easy - Tank building
        if (!world.isClientSide) {
            player.getItemInHand(hand);
            if (player.getItemInHand(hand).getItem() instanceof TankItem) {
                for (int i = 0; i < pos.getY() + 4; i++) {
                    if (world.isEmptyBlock(pos.offset(0, i, 0))) {
                        if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
                        world.setBlock(pos.above(i), this.defaultBlockState(), 3);
                        world.playSound(player, pos, SoundEvents.GLASS_PLACE, SoundCategory.BLOCKS, 0.0f, 0.0f);
                        break;
                    } else if (!world.isEmptyBlock(pos.above(i)) && world.getBlockState(pos.above(i)).getBlock() != this) {
                        break;
                    }
                }
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }

    @Override
    public void initSpecialRenderer() {
        ClientRegistry.bindTileEntityRenderer(Register.TILE.TANK, TankRenderer :: new);
    }

    private void setState(World world, BlockPos pos, TankState state) {
        world.setBlockAndUpdate(pos, defaultBlockState().setValue(STATE, state));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STATE);
    }

    @Override
    protected Class<TankTile> getTileClass() {
        return TankTile.class;
    }

    public TankTile getTank(IBlockReader world, BlockPos pos) {
        return (TankTile) world.getBlockEntity(pos);
    }

    @Override
    public void getWailaBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        TankTile tank = getTank(accessor.getWorld(), accessor.getPosition());

        if (tank != null) {
            tooltip.add(new StringTextComponent(TextHelper.formatCapacityInfo(tank.getTank().getFluidAmount(), tank.getTank().getCapacity(), "mB")));
            if(tank.getFluid().getFluid() != null) {
                String fluidName = Objects.requireNonNull(tank.getTank().getFluid().getDisplayName().getString());
                if(!Objects.equals(fluidName, "Air")) {
                    switch(fluidName) {
                        case "Water":
                            tooltip.add(new StringTextComponent("Fluid: " + TextFormatting.AQUA + fluidName));
                            break;
                        case "Lava":
                            tooltip.add(new StringTextComponent("Fluid: " + TextFormatting.RED + fluidName));
                            break;
                        default:
                            tooltip.add(new StringTextComponent("Fluid: " + TextFormatting.GREEN + fluidName));
                            break;
                    }
                }
            }
        }
    }

    /*@Override
    @OnlyIn(Dist.CLIENT)
    public int getColor(BlockState state, @Nullable IBlockDisplayReader blockDisplayReader, @Nullable BlockPos pos, int tint) {
        return colorIndex;
    }*/

}
