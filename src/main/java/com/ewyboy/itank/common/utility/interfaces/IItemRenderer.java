package com.ewyboy.ewysstorage.common.utility.interfaces;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemRenderer {
    @SideOnly(Side.CLIENT)
    void registerItemRenderer();
}