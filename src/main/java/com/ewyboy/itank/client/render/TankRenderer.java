package com.ewyboy.itank.client.render;

import com.ewyboy.itank.common.tiles.TileEntityTank;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TankRenderer extends TileEntitySpecialRenderer <TileEntityTank> {

    @Override
    public void render(TileEntityTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderFluid(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderFluid(TileEntityTank te) {
        if (te != null) {
            final FluidTank fluid = te.tank;
            if (fluid != null && fluid.getFluid() != null && fluid.getFluidAmount() > 0) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                FluidRenderHelper.renderFluidCuboid(te, fluid.getFluid(), te.getPos(),
                        0.15d, 0.00d, 0.15d,
                        0.00d, 0.00d, 0.00d,
                        0.70d, (double) fluid.getFluidAmount() / (double) fluid.getCapacity(), 0.70d);
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }
}
