package com.ewyboy.itank.common.network.packets;

import com.ewyboy.itank.common.tiles.TileEntityTank;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by EwyBoy
 **/
public class MessageFluidSync implements IMessage, IMessageHandler<MessageFluidSync, IMessage> {

    public String fluid;
    public int amount;
    public BlockPos pos;

    public MessageFluidSync() {}

    public MessageFluidSync(TileEntityTank tile) {
        this.fluid = "";
        if (tile.getTank().getFluid() != null) this.fluid = tile.getTank().getFluid().getFluid().getName();
        this.amount = tile.getTank().getFluidAmount();
        this.pos = tile.getPos();
    }

    public MessageFluidSync(String fluid, int amount, BlockPos pos) {
        this.fluid = fluid;
        this.amount = amount;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.fluid = ByteBufUtils.readUTF8String(buf);
        this.amount = buf.readInt();
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, fluid);
        buf.writeInt(amount);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    @Override
    public IMessage onMessage(final MessageFluidSync message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> handle(message));
        return null;
    }

    private void handle(MessageFluidSync message) {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.pos);
        if (tileEntity instanceof TileEntityTank) {
            Fluid fluid;
            FluidStack stack = null;
            if (!message.fluid.isEmpty()) {
                fluid = FluidRegistry.getFluid(message.fluid);
                stack = new FluidStack(fluid, message.amount);
            }
            ((TileEntityTank) tileEntity).getTank().setFluid(stack);
        }
    }
}