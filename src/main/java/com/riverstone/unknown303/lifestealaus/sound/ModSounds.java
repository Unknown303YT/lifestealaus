package com.riverstone.unknown303.lifestealaus.sound;

import com.riverstone.unknown303.lifestealaus.LifestealAUS;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent HEART_EQUIP = registerSoundEvent("heart.equip");
    public static final SoundEvent HEART_EQUIP_FAIL = registerSoundEvent("heart.equip.fail");
    public static final SoundEvent DEATH_BAN = registerSoundEvent("player.death_banned");
    public static final SoundEvent REVIVE = registerSoundEvent("player.revived");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(LifestealAUS.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {
        LifestealAUS.LOGGER.info("Registering Sounds for " + LifestealAUS.MOD_ID);
    }
}
