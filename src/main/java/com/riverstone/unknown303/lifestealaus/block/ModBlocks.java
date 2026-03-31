package com.riverstone.unknown303.lifestealaus.block;

import com.riverstone.unknown303.lifestealaus.LifestealAUS;
import com.riverstone.unknown303.lifestealaus.block.custom.ReviveBeaconBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
    public static final Block REVIVE_BEACON = registerBlock("revive_beacon",
            settings -> new ReviveBeaconBlock(settings.luminance(state -> 15).nonOpaque().solidBlock(Blocks::never)));

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> function) {
        Identifier id = Identifier.of(LifestealAUS.MOD_ID, name);
        Block block = function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, id)));
        registerBlockItem(id, block);
        return Registry.register(Registries.BLOCK, id, block);
    }

    private static void registerBlockItem(Identifier id, Block block) {
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, id))));
    }

    public static void register() {
        LifestealAUS.LOGGER.info("Registering Blocks for " + LifestealAUS.MOD_ID);
    }
}
