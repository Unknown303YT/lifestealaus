package com.riverstone.unknown303.lifestealaus.event;

import com.riverstone.unknown303.lifestealaus.data.HeartData;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModEvents {
    public static final ServerLivingEntityEvents.AfterDeath AFTER_DEATH = (entity, damageSource) -> {
        if (!(entity instanceof ServerPlayerEntity player))
            return;

        HeartData data = HeartData.get(player.getEntityWorld());

        data.removeHearts(player, 2);
        if (damageSource.getAttacker() instanceof ServerPlayerEntity attacker)
            data.addHearts(attacker, 2);
    };

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(AFTER_DEATH);
    }
}
