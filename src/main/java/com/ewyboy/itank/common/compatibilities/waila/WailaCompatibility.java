package com.ewyboy.itank.common.compatibilities.waila;

import com.ewyboy.itank.common.blocks.BlockTank;
import com.ewyboy.itank.common.loaders.BlockLoader;
import com.ewyboy.itank.common.utility.Reference;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import java.util.List;

/** Created by EwyBoy **/
public class WailaCompatibility implements IWailaDataProvider {

    public static final WailaCompatibility INSTANCE = new WailaCompatibility();

    private WailaCompatibility() {}

    private static boolean registered;
    private static boolean loaded;

    public static void load(IWailaRegistrar registrar) {
        System.out.println("WailaCompatibility.load");

        if (!registered) {
            throw new RuntimeException("Please register this handler using the provided method.");
        }

        if (!loaded) {
            registrar.registerStackProvider(INSTANCE, BlockTank.class);
            registrar.registerHeadProvider(INSTANCE, BlockTank.class);
            registrar.registerBodyProvider(INSTANCE, BlockTank.class);
            registrar.registerTailProvider(INSTANCE, BlockTank.class);
            loaded = true;
        }
    }

    public static void register() {
        if (registered) return; registered = true;
        FMLInterModComms.sendMessage("Waila", "register", Reference.Path.wailaPath);
    }


    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        return tag;
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(BlockLoader.tank);
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        if (block instanceof IWailaUser) {
            return ((IWailaUser) block).getWailaBody(itemStack, currenttip, accessor, config);
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }
}