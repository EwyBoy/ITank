package com.ewyboy.itank.common.blocks;

import com.ewyboy.bibliotheca.common.block.BlockBaseModeled;
import com.ewyboy.bibliotheca.common.compatibilities.waila.IWailaInformationUser;
import com.ewyboy.bibliotheca.common.interfaces.IBlockRenderer;
import com.ewyboy.itank.client.render.TankRenderer;
import com.ewyboy.itank.common.loaders.CreativeTabLoader;
import com.ewyboy.itank.common.tiles.TileEntityTank;
import com.ewyboy.itank.common.utility.ItemStackUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockTank extends BlockBaseModeled implements IBlockRenderer, IWailaInformationUser {

    public static final PropertyInteger STATE = PropertyInteger.create("state", 0, 3);

    public BlockTank() {
        super(Material.GLASS);
        setHardness(1.0f);
        setCreativeTab(CreativeTabLoader.ITank);
        setDefaultState(blockState.getBaseState());
    }

    public void setState(World world, BlockPos pos, int state) {
        TileEntity tileEntityOriginal = world.getTileEntity(pos);
        NBTTagCompound tag = new NBTTagCompound();

        tileEntityOriginal.writeToNBT(tag);
        world.setBlockState(pos, getDefaultState().withProperty(STATE, state));

        TileEntity tileEntityNew = world.getTileEntity(pos);
        tileEntityNew.readFromNBT(tag);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (world.getBlockState(pos.up()).getBlock() == this && world.getBlockState(pos.down()).getBlock() == this) {
            setState(world, pos, 1);
        } else if (world.getBlockState(pos.up()).getBlock() == this && world.getBlockState(pos.down()).getBlock() != this) {
            setState(world, pos, 2);
        } else if (world.getBlockState(pos.up()).getBlock() != this && world.getBlockState(pos.down()).getBlock() == this) {
            setState(world, pos, 3);
        } else {
            setState(world, pos, 0);
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

    @Override
    public int quantityDropped (Random random) {
        return 0;
    }

    @Override
    public boolean removedByPlayer (IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (!player.capabilities.isCreativeMode) {
            if (player.isSneaking()) {
                ItemStackUtils.dropStackInWorld(world, pos, new ItemStack(this));
            } else {
                final TileEntityTank tank = (TileEntityTank) world.getTileEntity(pos);
                ItemStackUtils.dropStackInWorld(world, pos, ItemStackUtils.createStackFromTileEntity(tank));
            }
        }
        return world.setBlockToAir(pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntityTank te = getTE(world, pos);
        /**Fluid input to TileEntity*/
        if (te != null) {
            if (FluidUtil.interactWithFluidHandler(player, EnumHand.MAIN_HAND, world, pos, facing)) return true;
        }

        /**Smart / Easy - Tank building*/
        if (!world.isRemote) {
            if (player.getHeldItem(hand) != null) {
                if (player.getHeldItem(hand).getItem().equals(Item.getItemFromBlock(this))) {
                    for (int i = 0; i < pos.getY() + 4; i++) {
                        if (world.isAirBlock(pos.add(0, i, 0))) {
                            if (!player.isCreative()) player.getHeldItem(hand).shrink(1);
                            world.setBlockState(pos.add(0, i, 0), this.getDefaultState(), 3);
                            break;
                        } else if (!world.isAirBlock(pos.add(0, i, 0)) && world.getBlockState(pos.add(0, i, 0)).getBlock() != this)
                            break;
                    }
                }
            }
        }
        return true;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasTagCompound()) {
            final TileEntityTank tank = (TileEntityTank) worldIn.getTileEntity(pos);
            if (tank != null) {
                tank.readNBT(stack.getTagCompound().getCompoundTag("TileData"));
            }
        }
    }

    @Override
    public void onBlockExploded (World world, BlockPos pos, Explosion explosion) {
        ItemStackUtils.dropStackInWorld(world, pos, ItemStackUtils.createStackFromTileEntity(world.getTileEntity(pos)));
        world.setBlockToAir(pos);
        this.onBlockDestroyedByExplosion(world, pos, explosion);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityTank) {
            TileEntityTank barrel = (TileEntityTank) te;
            if (barrel.tank.getFluid() != null && barrel.tank.getFluidAmount() > 0) {
                return barrel.tank.getFluid().getFluid().getLuminosity(barrel.tank.getFluid());
            }
        }
        return 0;
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class, new TankRenderer());
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Override
            @SideOnly(Side.CLIENT)
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

    public TileEntityTank getTE(IBlockAccess world, BlockPos pos) {
        return (TileEntityTank) world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityTank();
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, mcp.mobius.waila.api.IWailaDataAccessor accessor, mcp.mobius.waila.api.IWailaConfigHandler iWailaConfigHandler) {
        BlockPos pos = accessor.getPosition();
        World world = accessor.getWorld();
        TileEntityTank te = getTE(world, pos);

        if (te != null) {
            currenttip.add(te.tank.getFluid() != null ? te.tank.getFluid().getLocalizedName() : "EMPTY");
            currenttip.add(te.tank.getFluidAmount() + "/" + te.tank.getCapacity() + " mB");
        }
        return currenttip;
    }
}
