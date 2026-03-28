package com.riverstone.unknown303.lifestealaus.item;

import com.riverstone.unknown303.lifestealaus.LifestealAUS;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static Item registerItem(String name, Function<Item.Settings, Item> function) {
        Identifier id = Identifier.of(LifestealAUS.MOD_ID, name);
        return Registry.register(Registries.ITEM, id,
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, id))));
    }

    public static void register() {
        LifestealAUS.LOGGER.info("Registering Items for " + LifestealAUS.MOD_ID);
    }
}
