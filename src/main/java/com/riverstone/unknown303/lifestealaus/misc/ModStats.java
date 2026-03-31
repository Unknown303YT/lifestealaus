package com.riverstone.unknown303.lifestealaus.misc;

import com.riverstone.unknown303.lifestealaus.LifestealAUS;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class ModStats {
    public static final Identifier INTERACT_WITH_REVIVE_BEACON = registerStat("interact_with_revive_beacon", StatFormatter.DEFAULT);

    private static Identifier registerStat(String name, StatFormatter formatter) {
        Identifier id = Identifier.of(LifestealAUS.MOD_ID, name);
        Registry.register(Registries.CUSTOM_STAT, id, id);
        Stats.CUSTOM.getOrCreateStat(id, formatter);
        return id;
    }

    public static void register() {
        LifestealAUS.LOGGER.info("Registering Statistics for " + LifestealAUS.MOD_ID);
    }
}
