package com.riverstone.unknown303.lifestealaus.item.custom;

import com.riverstone.unknown303.lifestealaus.sound.ModSounds;
import com.riverstone.unknown303.lifestealaus.util.HeartData;
import com.riverstone.unknown303.lifestealaus.util.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
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
        user.playSound(ModSounds.HEART_EQUIP);
        if (!world.isClient())
            HeartData.addHearts((IEntityDataSaver) user, 2);

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode)
            itemStack.decrement(1);

        return ActionResult.SUCCESS;
    }
}
