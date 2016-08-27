package com.ewyboy.itank.client;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;
import java.util.Map;

/**@author shadowfacts*/
public class FluidRenderHelper {

    private static final Map<Fluid, ResourceLocation> cache = new HashMap<>();

    public static ResourceLocation getTexture(Fluid fluid) {
        if (cache.containsKey(fluid)) {
            return cache.get(fluid);
        }

        ResourceLocation still = fluid.getStill();

        if (still != null) {
            ResourceLocation texture = new ResourceLocation(still.getResourceDomain(), "textures/" + still.getResourcePath() + ".png");
            return cache.put(fluid, texture);
        }

        return null;
    }
}