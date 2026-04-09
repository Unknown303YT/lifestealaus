package com.riverstone.unknown303.lifestealaus.event;

import com.riverstone.unknown303.lifestealaus.data.HeartData;
import com.riverstone.unknown303.lifestealaus.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModEvents {
    public static final ServerPlayConnectionEvents.Join JOIN = (handler, sender, server) -> {
        ServerPlayerEntity player = handler.getPlayer();
        HeartData.get(player.getEntityWorld()).fixAttribute(player);
    };

    public static final ServerLivingEntityEvents.AfterDeath AFTER_DEATH = (entity, damageSource) -> {
        if (!(entity instanceof ServerPlayerEntity player))
            return;

        HeartData data = HeartData.get(player.getEntityWorld());

        data.removeHearts(player, 2);
        if (damageSource.getAttacker() instanceof ServerPlayerEntity attacker) {
            if (data.addHearts(attacker, 2) == -1) {
                ItemStack itemStack = new ItemStack(ModItems.HEART, 1);

                ItemEntity item = new ItemEntity(
                        player.getEntityWorld(),
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        itemStack
                );

                player.getEntityWorld().spawnEntity(item);
            }
        } else {
            ItemStack itemStack = new ItemStack(ModItems.HEART, 1);

            ItemEntity item = new ItemEntity(
                    player.getEntityWorld(),
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    itemStack
            );

            player.getEntityWorld().spawnEntity(item);
        }
    };
    public static final ServerPlayerEvents.CopyFrom COPY_FROM = (oldPlayer, newPlayer, alive) ->
            HeartData.get(newPlayer.getEntityWorld()).fixAttribute(newPlayer);

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(AFTER_DEATH);
        ServerPlayerEvents.COPY_FROM.register(COPY_FROM);
        ServerPlayConnectionEvents.JOIN.register(JOIN);
    }
}
