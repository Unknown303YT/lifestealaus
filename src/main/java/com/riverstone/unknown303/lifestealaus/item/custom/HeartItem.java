package com.riverstone.unknown303.lifestealaus.item.custom;

import com.riverstone.unknown303.lifestealaus.data.HeartData;
import com.riverstone.unknown303.lifestealaus.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HeartItem extends Item {
    public HeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient()) {
            HeartData data = HeartData.get((ServerWorld) world);
            double result = data.addHearts((ServerPlayerEntity) user, 2D);

            if (result == -1D) {
                user.sendMessage(Text.translatable("item.lifestealaus.heart.use.fail", data.getMaxHearts() / 2).withColor(0xFF5555), false);
                ModSounds.playSoundToPlayer((ServerPlayerEntity) user, ModSounds.HEART_EQUIP_FAIL, SoundCategory.PLAYERS, 1.0F, 0.5F);
                return ActionResult.FAIL;
            }

            ModSounds.playSoundToPlayer((ServerPlayerEntity) user, ModSounds.HEART_EQUIP, SoundCategory.PLAYERS);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode)
                itemStack.decrement(1);
        }

        return ActionResult.SUCCESS;
    }
}
