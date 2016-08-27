package com.ewyboy.itank.common.blocks;

import com.ewyboy.itank.client.render.TankRenderer;
import com.ewyboy.itank.common.compatibilities.waila.IWailaUser;
import com.ewyboy.itank.common.loaders.CreativeTabLoader;
import com.ewyboy.itank.common.tiles.TileTank;
import com.ewyboy.itank.common.utility.Reference;
import com.ewyboy.itank.common.utility.interfaces.IBlockRenderer;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockTank extends Block implements ITileEntityProvider, IWailaUser, IBlockRenderer {

    public static final PropertyInteger STATE = PropertyInteger.create("state", 0, 3);

    public BlockTank() {
        super(Material.GLASS);
        setUnlocalizedName(Reference.Blocks.tank);
        setRegistryName(Reference.Blocks.tank);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(TileTank.class, "tank");
        setCreativeTab(CreativeTabLoader.ITank);
        setDefaultState(blockState.getBaseState());
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof TileTank) {
            TileTank barrel = (TileTank)te;

            if (barrel.tank.getFluid() != null && barrel.tank.getFluidAmount() > 0) {
                return barrel.tank.getFluid().getFluid().getLuminosity(barrel.tank.getFluid());
            }
        }

        return 0;
    }


    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn) {
        if (world.getBlockState(pos.up()).getBlock() == this && world.getBlockState(pos.down()).getBlock() == this) {
            world.setBlockState(pos, state.withProperty(STATE, 1));
        } else if (world.getBlockState(pos.up()).getBlock() == this && world.getBlockState(pos.down()).getBlock() != this) {
            world.setBlockState(pos, state.withProperty(STATE, 2));
        } else if (world.getBlockState(pos.up()).getBlock() != this && world.getBlockState(pos.down()).getBlock() == this) {
            world.setBlockState(pos, state.withProperty(STATE, 3));
        } else {
            world.setBlockState(pos, state.withProperty(STATE, 0));
        }
    }


    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STATE, (meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(STATE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STATE);
    }

    private TileTank getTE(IBlockAccess world, BlockPos pos) {
        return (TileTank) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileTank te = getTE(world, pos);

        /**Fluid input to TileEntity*/
        if (te != null) {
            ItemStack input = player.getHeldItem(hand);
            if (FluidUtil.interactWithFluidHandler(input, te.tank, player)) return true;
        }

        /**Smart / Easy - Tank building*/
        if (!world.isRemote) {
            if (player.getHeldItem(hand) != null) {
                if (player.getHeldItem(hand).getItem().equals(Item.getItemFromBlock(this))) {
                    for (int i = 0; i < pos.getY() + 8; i++) {
                        if (world.isAirBlock(pos.add(0,i,0))) {
                            if (!player.isCreative()) player.getHeldItem(hand).stackSize--;
                            world.setBlockState(pos.add(0,i,0), this.getDefaultState(), 3);
                            break;
                        } else if (!world.isAirBlock(pos.add(0,i,0)) && world.getBlockState(pos.add(0,i,0)).getBlock() != this) break;
                    }
                }
            }
        }
        return true;
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileTank.class, new TankRenderer());

        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {

            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(getRegistryName(), getPropertyString(state.getProperties()));
            }
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), new ItemStack(this).getMetadata(), new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileTank();
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        BlockPos pos = accessor.getPosition();
        World world = accessor.getWorld();
        TileTank te = getTE(world, pos);

        if (te != null) {
            currenttip.add(te.tank.getFluid() != null ? "Liquid: " + te.tank.getFluid().getLocalizedName() : "Liquid: " + "EMPTY");
            currenttip.add(te.tank.getFluidAmount() + "/" + te.tank.getCapacity() + " mB");
        }
        return currenttip;
    }
}
