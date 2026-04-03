package com.riverstone.unknown303.lifestealaus.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.riverstone.unknown303.lifestealaus.data.HeartData;
import com.riverstone.unknown303.lifestealaus.item.ModItems;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ModCommands {
    public static final LiteralArgumentBuilder<ServerCommandSource> WITHDRAW_COMMAND = CommandManager.literal("withdraw")
            .then(CommandManager.argument("hearts", IntegerArgumentType.integer(1)))
            .requires(ServerCommandSource::isExecutedByPlayer)
            .executes(context -> {
                int toWithdraw = IntegerArgumentType.getInteger(context, "hearts") * 2;
                ServerPlayerEntity player = context.getSource().getPlayer();
                HeartData data = HeartData.get(player.getEntityWorld());
                if (toWithdraw >= data.getHearts(player.getUuid()) - 2) {
                    context.getSource().sendError(Text.translatable("commands.lifestealaus.withdraw.heart_fail"));
                    return 0;
                }

                data.removeHearts(player, toWithdraw);
                player.giveOrDropStack(new ItemStack(ModItems.HEART, toWithdraw));
                context.getSource().sendFeedback(() -> Text.translatable("commands.lifestealaus.withdraw.success", toWithdraw), false);
                return 1;
            });

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(WITHDRAW_COMMAND);
        });
    }
}
