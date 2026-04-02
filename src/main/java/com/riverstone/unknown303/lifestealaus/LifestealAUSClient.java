package com.riverstone.unknown303.lifestealaus;

import com.riverstone.unknown303.lifestealaus.block.ModBlocks;
import com.riverstone.unknown303.lifestealaus.block.entity.ModBlockEntities;
import com.riverstone.unknown303.lifestealaus.block.entity.custom.render.ReviveBeaconBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class LifestealAUSClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.REVIVE_BEACON, context -> new ReviveBeaconBlockEntityRenderer<>());
        BlockRenderLayerMap.putBlock(ModBlocks.REVIVE_BEACON, BlockRenderLayer.CUTOUT);
    }
}
