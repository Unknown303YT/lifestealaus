package com.riverstone.unknown303.lifestealaus.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.riverstone.unknown303.lifestealaus.data.HeartData;
import com.riverstone.unknown303.lifestealaus.item.ModItems;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.Collection;

public class ModCommands {
    public static final LiteralArgumentBuilder<ServerCommandSource> WITHDRAW_COMMAND = CommandManager.literal("withdraw")
            .then(CommandManager.argument("hearts", IntegerArgumentType.integer(1))
                    .executes(context -> {
                        int hearts = IntegerArgumentType.getInteger(context, "hearts");
                        ServerPlayerEntity player = context.getSource().getPlayer();
                        HeartData data = HeartData.get(player.getEntityWorld());
                        if ((hearts * 2) > data.getHearts(player.getUuid()) - 2) {
                            context.getSource().sendError(Text.translatable("commands.lifestealaus.withdraw.heart_fail"));
                            return 0;
                        }

                        data.removeHearts(player, hearts * 2);
                        player.giveOrDropStack(new ItemStack(ModItems.HEART, hearts));
                        context.getSource().sendFeedback(() -> Text.translatable("commands.lifestealaus.withdraw.success", hearts), false);
                        return 1;
                    }))
            .requires(ServerCommandSource::isExecutedByPlayer);

    public static final LiteralArgumentBuilder<ServerCommandSource> ADMIN_COMMAND = CommandManager.literal("lsaus")
            .then(
                    CommandManager.literal("fix").executes(context -> {
                        for (ServerWorld world : context.getSource().getServer().getWorlds()) {
                            HeartData data = HeartData.get(world);
                            for (ServerPlayerEntity player : world.getPlayers())
                                data.fixAttribute(player);
                        }
                        return 1;
                    })
            ).then(
                    CommandManager.literal("hearts").then(
                            CommandManager.literal("set").then(
                                    CommandManager.literal("all")
                                                    .then(CommandManager.argument("count", IntegerArgumentType.integer(1))
                                                            .executes(context ->
                                                                    HeartData.get(context.getSource().getWorld()).setAll(IntegerArgumentType.getInteger(context, "count"))))
                                            .then(
                                    CommandManager.argument("players", EntityArgumentType.players())
                                            .then(CommandManager.argument("count", IntegerArgumentType.integer(1))
                                                    .executes(context -> setHearts(IntegerArgumentType.getInteger(context, "count"), EntityArgumentType.getPlayers(context, "players")))))
                            ).then(
                                    CommandManager.literal("add").then(
                                            CommandManager.argument("players", EntityArgumentType.players())
                                                    .then(CommandManager.argument("count", IntegerArgumentType.integer(1))
                                                            .executes(context -> addHearts(IntegerArgumentType.getInteger(context, "count"), EntityArgumentType.getPlayers(context, "players"))))
                                    )
                            ).then(
                                    CommandManager.literal("remove").then(
                                            CommandManager.argument("players", EntityArgumentType.players())
                                                    .then(CommandManager.argument("count", IntegerArgumentType.integer(1))
                                                            .executes(context -> removeHearts(context, IntegerArgumentType.getInteger(context, "count"), EntityArgumentType.getPlayers(context, "players"))))
                                                    )
                                    )
                            ).then(CommandManager.literal("get").then(
                                    CommandManager.argument("player", EntityArgumentType.player())
                                            .executes(context -> {
                                                ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                                                context.getSource().sendFeedback(() -> Text.translatable("commands.lifestealaus.lsaus.hearts.get.success", player.getDisplayName(), HeartData.get(player.getEntityWorld()).getHearts(player.getUuid())), true);
                                                return 1;
                                            })
                    ))
            );

    private static int addHearts(int amount, Collection<ServerPlayerEntity> players) {
        double trueAmount = amount * 2;
        for (ServerPlayerEntity player : players)
            HeartData.get(player.getEntityWorld()).addHearts(player, trueAmount);

        return players.size();
    }

    private static int removeHearts(CommandContext<ServerCommandSource> context, int amount, Collection<ServerPlayerEntity> players) {
        double trueAmount = amount * 2;
        int failCount = 0;
        for (ServerPlayerEntity player : players) {
            HeartData data = HeartData.get(player.getEntityWorld());
            if (data.getHearts(player.getUuid()) - trueAmount <= 2) {
                context.getSource().sendError(Text.translatable("commands.lifestealaus.lsaus.hearts.remove.fail", player.getDisplayName()));
                failCount++;
                continue;
            }

            HeartData.get(player.getEntityWorld()).removeHearts(player, trueAmount);
        }

        return players.size() - failCount;
    }

    private static int setHearts(int amount, Collection<ServerPlayerEntity> players) {
        double trueAmount = amount * 2;
        for (ServerPlayerEntity player : players) {
            HeartData data = HeartData.get(player.getEntityWorld());
            double hearts = data.getHearts(player.getUuid());
            if (trueAmount == hearts)
                continue;
            if (trueAmount > hearts)
                data.addHearts(player, trueAmount - hearts);
            else
                data.removeHearts(player, hearts - trueAmount);
        }

        return players.size();
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(WITHDRAW_COMMAND);
            LiteralCommandNode<ServerCommandSource> adminCommand = dispatcher.register(ADMIN_COMMAND);
            dispatcher.register(CommandManager.literal("ls").redirect(adminCommand));
        });
    }
}
