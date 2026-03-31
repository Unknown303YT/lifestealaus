package com.riverstone.unknown303.lifestealaus.block.entity;

import com.riverstone.unknown303.lifestealaus.LifestealAUS;
import com.riverstone.unknown303.lifestealaus.block.ModBlocks;
import com.riverstone.unknown303.lifestealaus.block.entity.custom.ReviveBeaconBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<ReviveBeaconBlockEntity> REVIVE_BEACON =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(LifestealAUS.MOD_ID, "revive_beacon"),
                    FabricBlockEntityTypeBuilder.create(ReviveBeaconBlockEntity::new, ModBlocks.REVIVE_BEACON).build());

    public static void register() {
        LifestealAUS.LOGGER.info("Registering Block Entities for " + LifestealAUS.MOD_ID);
    }
}
