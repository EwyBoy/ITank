package com.ewyboy.itank.common.network;

import com.ewyboy.itank.common.network.packets.MessageFluidSync;
import com.ewyboy.itank.common.utility.Reference;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by EwyBoy
 **/
public class ITankPacketHandler {

    private static int packetID = 0;
    public static SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Reference.ModInfo.ModID);

    private static int nextID() {
        return packetID++;
    }

    public static void registerMessages() {
        INSTANCE.registerMessage(MessageFluidSync.class, MessageFluidSync.class, nextID(), Side.CLIENT);
    }

}