package com.ewyboy.itank.client.render;

import com.ewyboy.itank.common.tiles.TileEntityTank;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidTank;

public class TankRenderer extends TileEntitySpecialRenderer <TileEntityTank> {

    @Override
    public void renderTileEntityAt(TileEntityTank te, double x, double y, double z, float partialTicks, int destroyStage) {
        renderFluidNew(te);
    }

    private void renderFluidNew(TileEntityTank te) {
        if (te != null) {
            final FluidTank fluid = te.tank;
            if (fluid != null && fluid.getFluid() != null && fluid.getFluidAmount() > 0) {
                GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                        RenderUtils.translateAgainstPlayer(te.getPos(), false);
                        RenderUtils.renderFluid(te, fluid.getFluid(), te.getPos(),
                                0.15d, 0.00d, 0.15d,
                                0.00d, 0.00d, 0.00d,
                                0.70d, (double) fluid.getFluidAmount() / (double) fluid.getCapacity(), 0.70d);
                    GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }
}
