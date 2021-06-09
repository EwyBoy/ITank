package com.ewyboy.itank.server;

import com.ewyboy.itank.ITank;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

public class CommandCenter {

    public CommandCenter(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSource> literal(ITank.MOD_ID)
                        .executes(ctx -> 0)
        );
    }

}
