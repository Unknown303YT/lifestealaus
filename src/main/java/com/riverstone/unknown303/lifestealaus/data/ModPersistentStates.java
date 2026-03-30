package com.riverstone.unknown303.lifestealaus.data;

import net.minecraft.world.PersistentStateType;

public class ModPersistentStates {
    public static final PersistentStateType<HeartData> HEART_DATA =
            new PersistentStateType<>("hearts", HeartData::new, HeartData.CODEC, null);
}
