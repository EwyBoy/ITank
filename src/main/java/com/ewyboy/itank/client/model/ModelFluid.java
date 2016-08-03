package com.ewyboy.ewysstorage.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFluid extends ModelBase {

    public ModelRenderer fluid;

    private ModelBox modelBox;

    public ModelFluid() {
        this.textureWidth = 16;
        this.textureHeight = 512;
        this.fluid = new ModelRenderer(this);
        modelBox = new ModelBox(this.fluid , 0, 0, -5, -7, -5, 10, 14, 10, 0.0F);
        this.fluid.cubeList.add(modelBox);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.fluid.render(f5);
    }

    public void renderAll() {
        float f5 = 0.0625f;
        this.fluid.cubeList.clear();
        this.fluid.cubeList.add(this.modelBox);
        this.fluid.render(f5);
    }
}