package com.ewyboy.itank.client;

import com.ewyboy.itank.common.content.tank.TankBlock;
import com.ewyboy.itank.common.content.tank.TankTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class TankRenderer implements BlockEntityRenderer<TankTile> {

    public TankRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(TankTile tank, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int light, int overlay) {
        FluidStack fluidStack = tank.getFluid();
        if (!fluidStack.isEmpty()) {
            int amount = fluidStack.getAmount();
            int total = tank.getTank().getTankCapacity(0);
            this.renderFluidInTank(tank.getLevel(), tank.getBlockPos(), fluidStack, matrix, buffer, (amount / (float) total));
        }
    }

    private void renderFluidInTank(BlockAndTintGetter world, BlockPos pos, FluidStack fluidStack, PoseStack matrix, MultiBufferSource buffer, float percent) {
        matrix.pushPose();
        matrix.translate(0.5d, 0.5d, 0.5d);
        Matrix4f matrix4f = matrix.last().pose();
        Matrix3f matrix3f = matrix.last().normal();

        Fluid fluid = fluidStack.getFluid();
        IClientFluidTypeExtensions fluidAttributes = IClientFluidTypeExtensions.of(fluid);
        TextureAtlasSprite fluidTexture = getFluidStillSprite(fluidAttributes, fluidStack);

        int color = fluidAttributes.getTintColor(fluidStack);

        VertexConsumer builder = buffer.getBuffer(RenderType.translucent());

        for (int i = 0; i < 4; i++) {
            this.renderNorthFluidFace(fluidTexture, matrix4f, matrix3f, builder, color, percent);
            matrix.mulPose(Axis.YP.rotationDegrees(90));
        }

        TankTile above;
        TankTile target;

        if (world.getBlockEntity(pos.above()) instanceof TankTile) {

            above = (TankTile) world.getBlockEntity(pos.above());
            target = (TankTile) world.getBlockEntity(pos);

            if (above != null && target != null) {
                if (above.getBlockState().hasProperty(TankBlock.COLOR) && target.getBlockState().hasProperty(TankBlock.COLOR)) {
                    if (!above.getBlockState().getValue(TankBlock.COLOR).equals(world.getBlockState(pos).getValue(TankBlock.COLOR))) {
                        this.renderTopFluidFace(fluidTexture, matrix4f, matrix3f, builder, color, percent);
                    } else if (above.getFluid().getAmount() <= 0 || !fluid.isSame(above.getFluid().getFluid())) {
                        this.renderTopFluidFace(fluidTexture, matrix4f, matrix3f, builder, color, percent);
                    }
                }
            }
        } else {
            if (percent != 1.0f) this.renderTopFluidFace(fluidTexture, matrix4f, matrix3f, builder, color, percent);
        }

        matrix.popPose();
    }

    private void renderTopFluidFace(TextureAtlasSprite sprite, Matrix4f matrix4f, Matrix3f normalMatrix, VertexConsumer builder, int color, float percent) {
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
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.vertex(matrix4f, -width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
                .uv(minU, maxV)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.vertex(matrix4f, width / 2, -height / 2 + percent * height, width / 2).color(r, g, b, a)
                .uv(maxU, maxV)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.vertex(matrix4f, width / 2, -height / 2 + percent * height, -width / 2).color(r, g, b, a)
                .uv(maxU, minV)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 1, 0)
                .endVertex();
    }

    private void renderNorthFluidFace(TextureAtlasSprite sprite, Matrix4f matrix4f, Matrix3f normalMatrix, VertexConsumer builder, int color, float percent) {
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
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 0, 1)
                .endVertex();

        builder.vertex(matrix4f, width / 2, -height / 2 + height * percent, (-width / 2) + 0.001f).color(r, g, b, a)
                .uv(maxU, minV)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 0, 1)
                .endVertex();

        builder.vertex(matrix4f, width / 2, -height / 2, (-width / 2) + 0.001f).color(r, g, b, a)
                .uv(maxU, maxV)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 0, 1)
                .endVertex();

        builder.vertex(matrix4f, -width / 2, -height / 2, (-width / 2) + 0.001f).color(r, g, b, a)
                .uv(minU, maxV)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0, 0, 1)
                .endVertex();
    }

    private TextureAtlasSprite getFluidStillSprite(IClientFluidTypeExtensions properties, FluidStack fluidStack) {
        return Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(properties.getStillTexture(fluidStack));
    }

    private TextureAtlasSprite getFluidFlowingSprite(IClientFluidTypeExtensions properties, FluidStack fluidStack) {
        return Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(properties.getFlowingTexture(fluidStack));
    }

}
