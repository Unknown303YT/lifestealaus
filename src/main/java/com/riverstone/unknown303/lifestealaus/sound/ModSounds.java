package com.riverstone.unknown303.lifestealaus.sound;

import com.riverstone.unknown303.lifestealaus.LifestealAUS;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
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

    public static void playSoundToPlayer(ServerPlayerEntity player, SoundEvent sound, SoundCategory category) {
        playSoundToPlayer(player, sound, category, 1.0F, 1.0F);
    }

    public static void playSoundToPlayer(ServerPlayerEntity player, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        player.networkHandler.sendPacket(new PlaySoundS2CPacket(
                Registries.SOUND_EVENT.getEntry(sound),
                category,
                player.getX(),
                player.getY(),
                player.getZ(),
                volume,
                pitch,
                player.getRandom().nextLong()
        ));
    }
}
