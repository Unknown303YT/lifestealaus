package com.riverstone.unknown303.lifestealaus.util;

import net.minecraft.nbt.NbtCompound;

public class HeartData {
    public static int addHearts(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int hearts = nbt.getInt("hearts", 20);

        // check and update heart count

        nbt.putInt("hearts", hearts);
        return hearts;
    }

    public static int removeHearts(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int hearts = nbt.getInt("hearts", 20);

        // check and update heart count

        nbt.putInt("hearts", hearts);
        // syncThirst(thirst, (ServerPlayerEntity) player);
        return hearts;
    }
}
