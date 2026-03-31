package com.riverstone.unknown303.lifestealaus;

import com.riverstone.unknown303.lifestealaus.block.ModBlocks;
import com.riverstone.unknown303.lifestealaus.block.entity.ModBlockEntities;
import com.riverstone.unknown303.lifestealaus.event.ModEvents;
import com.riverstone.unknown303.lifestealaus.item.ModItemGroups;
import com.riverstone.unknown303.lifestealaus.item.ModItems;
import com.riverstone.unknown303.lifestealaus.misc.ModStats;
import com.riverstone.unknown303.lifestealaus.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifestealAUS implements ModInitializer {
	public static final String MOD_ID = "lifestealaus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing LifestealAUS...");
        ModItemGroups.register();

        ModStats.register();

        ModItems.register();
        ModBlocks.register();

        LOGGER.info("Initializing BuiltIn Resource Pack...");
        ResourceLoader.registerBuiltinPack(
                Identifier.of(MOD_ID, "lifestealaus_custom_textures"),
                FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
                PackActivationType.NORMAL);
        LOGGER.info("BuiltIn Resource Pack Initialized!");

        ModEvents.register();

        ModBlockEntities.register();
        ModSounds.register();
		LOGGER.info("LifestealAUS Initialized!");
	}
}