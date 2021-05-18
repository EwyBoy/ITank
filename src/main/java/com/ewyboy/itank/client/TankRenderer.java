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
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.fluids.FluidAttributes;
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
            this.renderFluidInTank(tank.getLevel(), tank.getBlockPos(), fluidStack, matrix, buffer, (amount / (float) total));
        }
    }

    private void renderFluidInTank(IBlockDisplayReader world, BlockPos pos, FluidStack fluidStack, MatrixStack matrix, IRenderTypeBuffer buffer, float percent) {
        matrix.pushPose();
        matrix.translate(0.5d, 0.5d, 0.5d);
        Matrix4f matrix4f = matrix.last().pose();
        Matrix3f matrix3f = matrix.last().normal();

        Fluid fluid = fluidStack.getFluid();
        FluidAttributes fluidAttributes = fluid.getAttributes();
        TextureAtlasSprite fluidTexture = Minecraft.getInstance()
                .getTextureAtlas(PlayerContainer.BLOCK_ATLAS)
                .apply(fluidAttributes.getStillTexture(fluidStack));

        int color = fluidAttributes.getColor(fluidStack);

        IVertexBuilder builder = buffer.getBuffer(RenderType.translucent());

        for (int i = 0; i < 4; i++) {
            this.renderNorthFluidFace(fluidTexture, matrix4f, matrix3f, builder, color, percent);
            matrix.mulPose(Vector3f.YP.rotationDegrees(90));
        }

        TankTile above;

        if (world.getBlockEntity(pos.above()) instanceof TankTile) {
            above = (TankTile) world.getBlockEntity(pos.above());
            if(Objects.requireNonNull(above).getFluid() == null || above.getFluid().getAmount() <= 0 || !fluidStack.isFluidEqual(above.getFluid())) {
                this.renderTopFluidFace(fluidTexture, matrix4f, matrix3f, builder, color, percent);
            }
        } else {
            if (percent != 1.0f) this.renderTopFluidFace(fluidTexture, matrix4f, matrix3f, builder, color, percent);
        }

        matrix.popPose();
    }

    private void renderTopFluidFace(TextureAtlasSprite sprite, Matrix4f matrix4f, Matrix3f normalMatrix, IVertexBuilder builder, int color, float percent) {
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = ((color) & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;

        float width = 12 / 16f;
        float height = 16 / 16f;

        float minU = sprite.getU(3);
        float maxU = sprite.getU(13);
        float minV = sprite.getV(3);
        float maxV = sprite.getV(13);

        builder.vertex(matrix4f, -width / 2, -height / 2 + percent * height, -width / 2).color(r, g, b, a)
                .uv(minU, minV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.vertex(matrix4f, -width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
                .uv(minU, maxV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.vertex(matrix4f, width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
                .uv(maxU, maxV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.vertex(matrix4f, width / 2, -height / 2 + percent * height, -width / 2).color(r, g, b, a)
                .uv(maxU, minV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normalMatrix, 0, 1, 0)
                .endVertex();
    }

    private void renderNorthFluidFace(TextureAtlasSprite sprite, Matrix4f matrix4f, Matrix3f normalMatrix, IVertexBuilder builder, int color, float percent) {
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = ((color) & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;

        float width = 12 / 16f;
        float height = 16 / 16f;

        float minU = sprite.getU(3);
        float maxU = sprite.getU(13);
        float minV = sprite.getV(1);
        float maxV = sprite.getV(15 * percent);

        builder.vertex(matrix4f, -width / 2, -height / 2 + height * percent, (-width / 2) + 0.001f).color(r, g, b, a)
                .uv(minU, minV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normalMatrix, 0, 0, 1)
                .endVertex();

        builder.vertex(matrix4f, width / 2, -height / 2 + height * percent, (-width / 2) + 0.001f).color(r, g, b, a)
                .uv(maxU, minV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normalMatrix, 0, 0, 1)
                .endVertex();

        builder.vertex(matrix4f, width / 2, -height / 2, (-width / 2) + 0.001f).color(r, g, b, a)
                .uv(maxU, maxV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normalMatrix, 0, 0, 1)
                .endVertex();

        builder.vertex(matrix4f, -width / 2, -height / 2, (-width / 2) + 0.001f).color(r, g, b, a)
                .uv(minU, maxV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(normalMatrix, 0, 0, 1)
                .endVertex();
    }


    private static void putVertex(IVertexBuilder builder, MatrixStack ms, float x, float y, float z, int color, float u,
                                  float v, Direction face, int light) {

        Vector3i n = face.getNormal();
        MatrixStack.Entry peek = ms.last();

        int ff = 0xff;
        int a = color >> 24 & ff;
        int r = color >> 16 & ff;
        int g = color >> 8 & ff;
        int b = color & ff;

        builder.vertex(peek.pose(), x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(n.getX(), n.getY(), n.getZ())
                .endVertex();
    }

}
