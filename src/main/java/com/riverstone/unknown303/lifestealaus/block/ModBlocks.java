package com.riverstone.unknown303.lifestealaus.block;

import com.riverstone.unknown303.lifestealaus.LifestealAUS;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    private static Block registerBlock(String name, Block block) {
        Identifier id = Identifier.of(LifestealAUS.MOD_ID, name);
        registerBlockItem(id, block);
        return Registry.register(Registries.BLOCK, id, block);
    }

    private static void registerBlockItem(Identifier id, Block block) {
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
    }

    public static void register() {
        LifestealAUS.LOGGER.info("Registering Blocks for " + LifestealAUS.MOD_ID);
    }
}
