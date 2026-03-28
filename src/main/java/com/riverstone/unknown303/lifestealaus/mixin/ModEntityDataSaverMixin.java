package com.riverstone.unknown303.lifestealaus.mixin;

import com.riverstone.unknown303.lifestealaus.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class ModEntityDataSaverMixin implements IEntityDataSaver {
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if (persistentData == null)
            this.persistentData = new NbtCompound();

        return persistentData;
    }

    @Inject(method = "writeData", at = @At("HEAD"))
    protected void injectWriteMethod(WriteView view, CallbackInfo callbackInfo) {
        if (persistentData != null)
            view.put("lifestealaus.data", NbtCompound.CODEC, persistentData);
    }

    @Inject(method = "readData", at = @At("HEAD"))
    protected void injectReadMethod(ReadView view, CallbackInfo callbackInfo) {
        if (view.contains("lifestealaus.data"))
            persistentData = view.read("lifestealaus.data", NbtCompound.CODEC).orElse(null);
    }
}
