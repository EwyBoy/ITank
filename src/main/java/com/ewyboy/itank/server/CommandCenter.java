package com.ewyboy.itank.server;

import com.ewyboy.itank.ITank;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class CommandCenter {

    public CommandCenter(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack> literal(ITank.MOD_ID)
                        .executes(ctx -> 0)
        );
    }

}
