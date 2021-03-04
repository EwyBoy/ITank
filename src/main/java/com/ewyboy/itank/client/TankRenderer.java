package com.ewyboy.itank.client;

import com.ewyboy.itank.common.content.tank.TankTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

public class TankRenderer extends TileEntityRenderer<TankTile> {

    public TankRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TankTile tank, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        FluidStack fluidStack = tank.getFluid();
        if (!fluidStack.isEmpty()) {
            int amount = fluidStack.getAmount();
            int total = tank.getTank().getTankCapacity(0);
            this.renderFluidInTank(tank.getWorld(), tank.getPos(), fluidStack, matrix, buffer, (amount / (float) total));
        }
    }

    private void renderFluidInTank(IBlockDisplayReader world, BlockPos pos, FluidStack fluid, MatrixStack matrix, IRenderTypeBuffer buffer, float percent) {
        matrix.push();
        matrix.translate(0.5d, 0.5d, 0.5d);
        Matrix4f matrix4f = matrix.getLast().getMatrix();
        Matrix3f matrix3f = matrix.getLast().getNormal();
        int color = fluid.getFluid().getAttributes().getColor(world, pos);
        TextureAtlasSprite sprite = this.getFluidStillSprite(fluid.getFluid());
        IVertexBuilder builder = buffer.getBuffer(RenderType.getText(sprite.getAtlasTexture().getTextureLocation()));
        for (int i = 0; i < 4; i++) {
            this.renderNorthFluidFace(sprite, matrix4f, matrix3f, builder, color, percent);
            matrix.rotate(Vector3f.YP.rotationDegrees(90));
        }

        TankTile above;

        if (world.getTileEntity(pos.up()) instanceof TankTile) {
            above = (TankTile) world.getTileEntity(pos.up());
            if(Objects.requireNonNull(above).getFluid() == null || above.getFluid().getAmount() <= 0 || !fluid.isFluidEqual(above.getFluid())) {
                this.renderTopFluidFace(sprite, matrix4f, matrix3f, builder, color, percent);
            }
        } else {
            if (percent != 1.0f) this.renderTopFluidFace(sprite, matrix4f, matrix3f, builder, color, percent);
        }


        matrix.pop();
    }

    private void renderTopFluidFace(TextureAtlasSprite sprite, Matrix4f matrix4f, Matrix3f normalMatrix, IVertexBuilder builder, int color, float percent) {
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = ((color) & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;

        float width = 12 / 16f;
        float height = 16 / 16f;

        float minU = sprite.getInterpolatedU(3);
        float maxU = sprite.getInterpolatedU(13);
        float minV = sprite.getInterpolatedV(3);
        float maxV = sprite.getInterpolatedV(13);

        builder.pos(matrix4f, -width / 2, -height / 2 + percent * height, -width / 2).color(r, g, b, a)
                .tex(minU, minV)
                .overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.pos(matrix4f, -width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
                .tex(minU, maxV)
                .overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.pos(matrix4f, width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
                .tex(maxU, maxV)
                .overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.pos(matrix4f, width / 2, -height / 2 + percent * height, -width / 2).color(r, g, b, a)
                .tex(maxU, minV)
                .overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(normalMatrix, 0, 1, 0)
                .endVertex();
    }

    private void renderNorthFluidFace(TextureAtlasSprite sprite, Matrix4f matrix4f, Matrix3f normalMatrix, IVertexBuilder builder, int color, float percent) {
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = ((color) & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;

        float width = 12 / 16f;
        float height = 16 / 16f;

        float minU = sprite.getInterpolatedU(3);
        float maxU = sprite.getInterpolatedU(13);
        float minV = sprite.getInterpolatedV(1);
        float maxV = sprite.getInterpolatedV(15 * percent);

        builder.pos(matrix4f, -width / 2, -height / 2 + height * percent, (-width / 2) + 0.001f).color(r, g, b, a)
                .tex(minU, minV)
                .overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(normalMatrix, 0, 0, 1)
                .endVertex();

        builder.pos(matrix4f, width / 2, -height / 2 + height * percent, (-width / 2) + 0.001f).color(r, g, b, a)
                .tex(maxU, minV)
                .overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(normalMatrix, 0, 0, 1)
                .endVertex();

        builder.pos(matrix4f, width / 2, -height / 2, (-width / 2) + 0.001f).color(r, g, b, a)
                .tex(maxU, maxV)
                .overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(normalMatrix, 0, 0, 1)
                .endVertex();

        builder.pos(matrix4f, -width / 2, -height / 2, (-width / 2) + 0.001f).color(r, g, b, a)
                .tex(minU, maxV)
                .overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(normalMatrix, 0, 0, 1)
                .endVertex();
    }

    private TextureAtlasSprite getFluidStillSprite(Fluid fluid) {
        return Minecraft.getInstance()
                .getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE)
                .apply(fluid.getAttributes().getStillTexture());
    }

    private TextureAtlasSprite getFluidFlowingSprite(Fluid fluid) {
        return Minecraft.getInstance()
                .getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE)
                .apply(fluid.getAttributes().getFlowingTexture());
    }

}
