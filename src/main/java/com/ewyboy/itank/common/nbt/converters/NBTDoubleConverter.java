package com.ewyboy.itank.common.nbt.converters;

import com.ewyboy.itank.common.nbt.INBTConverter;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Jared on 7/26/2016.
 */
public class NBTDoubleConverter implements INBTConverter<Double> {

    private String name;

    public NBTDoubleConverter() {
    }

    @Override
    public NBTDoubleConverter setKey(String key) {
        this.name = key;
        return this;
    }

    @Override
    public Double convert(NBTTagCompound tag) {
        return tag.getDouble(name);
    }

    @Override
    public NBTTagCompound convert(NBTTagCompound tag, Double value) {
        tag.setDouble(name, value);
        return tag;
    }
}

