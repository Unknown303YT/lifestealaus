package com.riverstone.unknown303.lifestealaus.mixin;

import com.riverstone.unknown303.lifestealaus.data.HeartData;
import net.minecraft.server.PlayerConfigEntry;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Inject(method = "checkCanJoin", at = @At("HEAD"), cancellable = true)
    private void checkBanned(SocketAddress address, PlayerConfigEntry configEntry, CallbackInfoReturnable<Text> callbackInfo) {
        HeartData data = HeartData.get(((PlayerManager) (Object) this).getServer().getOverworld());
        if (data.getHearts(configEntry.id()) <= 0)
            callbackInfo.setReturnValue(HeartData.DEATH_BANNED_MSG);
    }
}
