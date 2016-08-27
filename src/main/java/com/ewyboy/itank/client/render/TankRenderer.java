package com.ewyboy.itank.client.render;

import com.ewyboy.itank.client.FluidRenderHelper;
import com.ewyboy.itank.client.model.ModelFluid;
import com.ewyboy.itank.common.tiles.TileTank;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

public class TankRenderer extends TileEntitySpecialRenderer <TileTank> {

    private static ModelFluid fluidModel = new ModelFluid();

    @Override
    public void renderTileEntityAt(TileTank te, double x, double y, double z, float partialTicks, int destroyStage) {
        renderFluid(te, x, y, z);
    }

    private void renderFluid(TileTank te, double x, double y, double z) {
        if (te.tank.getFluid() != null && te.tank.getFluidAmount() > 0) {
            ResourceLocation fluidTexture = FluidRenderHelper.getTexture(te.tank.getFluid().getFluid());

            int color = te.tank.getFluid().getFluid().getColor(te.tank.getFluid());
            float r, g, b;

            r = ((color >> 16) & 0xFF) / 255f;
            g = ((color >> 8) & 0xFF) / 255f;
            b = (color & 0xFF) / 255f;

            if (fluidTexture != null) {
                glPushMatrix();
                    translate((float) x + 0.5f, (float) y, (float) z + 0.5f);
                    rotate(180, 1, 0, 0);
                    enableBlend();
                        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        double fluidPercent = (int) (((double) te.tank.getFluidAmount() / (double) te.tank.getCapacity()) * 100);
                        scale(0.85, (fluidPercent / 100), 0.85);
                        translate(0, -0.5, 0);
                        bindTexture(fluidTexture);
                        color(r, g, b);
                        fluidModel.renderAll();
                    disableBlend();
                glPopMatrix();
            }
        }
    }
}
