package com.riverstone.unknown303.lifestealaus.item;

import com.riverstone.unknown303.lifestealaus.LifestealAUS;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup LIFESTEALAUS_ITEM_GROUP =
            Registry.register(Registries.ITEM_GROUP,
                    Identifier.of(LifestealAUS.MOD_ID, "lifestealaus_item_group"),
                    FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.HEART))
                            .displayName(Text.translatable("itemGroup.lifestealaus.lifestealaus_item_group"))
                            .entries(((displayContext, entries) -> {
                                entries.add(ModItems.HEART);
                            })).build());

    public static void register() {
        LifestealAUS.LOGGER.info("Registering Item Groups for " + LifestealAUS.MOD_ID);
    }
}
