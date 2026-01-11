package com.riverstone.unknown303.lifestealaus;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifestealAUS implements ModInitializer {
	public static final String MOD_ID = "lifestealaus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing LifestealAUS...");

		LOGGER.info("LifestealAUS Initialized!");
	}
}