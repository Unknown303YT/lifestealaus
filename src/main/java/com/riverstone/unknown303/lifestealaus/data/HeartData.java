package com.riverstone.unknown303.lifestealaus.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.riverstone.unknown303.lifestealaus.LifestealAUS;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeartData extends PersistentState {
    public static final Codec<HeartData> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(Codec.unboundedMap(Codec.STRING.xmap(UUID::fromString, UUID::toString), Codec.DOUBLE)
                    .fieldOf("hearts")
                    .orElse(new HashMap<>())
                    .forGetter(data -> data.hearts)).apply(instance, HeartData::new));

    private static final Text DEATH_BANNED_MSG = Text.translatable("multiplayer.lifestealaus.disconnect.banned.death")
            .withColor(0xFF5555);
    public static HeartData get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(ModPersistentStates.HEART_DATA);
    }

    private final Map<UUID, Double> hearts;

    private HeartData(Map<UUID, Double> hearts) {
        this.hearts = hearts;
    }

    public HeartData() {
        this.hearts = new HashMap<>();
    }

    public double getHearts(UUID player) {
        return hearts.getOrDefault(player, 20D);
    }

    public double addHearts(ServerPlayerEntity player, double amount) {
        double heartCount = getHearts(player.getUuid()) + amount;
        hearts.put(player.getUuid(), heartCount);
        markDirty();
        player.getAttributeInstance(EntityAttributes.MAX_HEALTH).addTemporaryModifier(createHeartsModifier(heartCount));
        return heartCount;
    }

    public double removeHearts(ServerPlayerEntity player, double amount) {
        double heartCount = getHearts(player.getUuid()) - amount;
        hearts.put(player.getUuid(), heartCount);
        markDirty();
        if (isDeathBanned(player)) {
            player.getEntityWorld().getServer().sendMessage(createBannedChatMsg(player));
            player.networkHandler.disconnect(DEATH_BANNED_MSG);
        }
        player.getAttributeInstance(EntityAttributes.MAX_HEALTH).addTemporaryModifier(createHeartsModifier(heartCount));
        return heartCount;
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    private boolean isDeathBanned(ServerPlayerEntity player) {
        return getHearts(player.getUuid()) <= 0;
    }

    private Text createBannedChatMsg(ServerPlayerEntity player) {
        return Text.translatable("multiplayer.lifestealaus.player.left.banned", player.getDisplayName()).withColor(0xFF5555);
    }

    private EntityAttributeModifier createHeartsModifier(double hearts) {
        return new EntityAttributeModifier(Identifier.of(LifestealAUS.MOD_ID, "hearts"), hearts - 20D, EntityAttributeModifier.Operation.ADD_VALUE);
    }
}
