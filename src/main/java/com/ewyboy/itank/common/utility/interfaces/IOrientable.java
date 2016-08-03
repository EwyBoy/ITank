package com.ewyboy.ewysstorage.common.utility.interfaces;

import net.minecraft.util.EnumFacing;

public interface IOrientable {
    boolean canBeRotated();

    EnumFacing getForward();

    void setOrientation(EnumFacing forward);
}
