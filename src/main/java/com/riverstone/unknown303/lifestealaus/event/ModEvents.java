package com.riverstone.unknown303.lifestealaus.event;

import com.riverstone.unknown303.lifestealaus.data.HeartData;
import com.riverstone.unknown303.lifestealaus.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModEvents {
    public static final ServerPlayerEvents.Join JOIN = player -> {
        HeartData.get(player.getEntityWorld()).fixAttribute(player);
    };

    public static final ServerLivingEntityEvents.AfterDeath AFTER_DEATH = (entity, damageSource) -> {
        if (!(entity instanceof ServerPlayerEntity player))
            return;

        HeartData data = HeartData.get(player.getEntityWorld());

        data.removeHearts(player, 2);
        if (damageSource.getAttacker() instanceof ServerPlayerEntity attacker) {
            data.addHearts(attacker, 2);
            data.fixAttribute(attacker);
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
    public static final ServerPlayerEvents.AfterRespawn AFTER_RESPAWN = ((oldPlayer, newPlayer, alive) -> {
        if (!alive)
            return;

        HeartData.get(newPlayer.getEntityWorld()).fixAttribute(newPlayer);
    });

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(AFTER_DEATH);
        ServerPlayerEvents.AFTER_RESPAWN.register(AFTER_RESPAWN);
        ServerPlayerEvents.JOIN.register(JOIN);
    }
}
