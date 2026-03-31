package com.riverstone.unknown303.lifestealaus.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.riverstone.unknown303.lifestealaus.data.HeartData;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModCommands {
    public static final LiteralArgumentBuilder<ServerCommandSource> WITHDRAW_COMMAND = CommandManager.literal("withdraw")
            .then(CommandManager.argument("hearts", IntegerArgumentType.integer()))
            .requires(ServerCommandSource::isExecutedByPlayer)
            .executes(context -> {
                int toWithdraw = context.getArgument("hearts", Integer.class);
                ServerPlayerEntity player = context.getSource().getPlayer();
                HeartData data = HeartData.get(player.getEntityWorld());
                if (toWithdraw >= )
            })
}
