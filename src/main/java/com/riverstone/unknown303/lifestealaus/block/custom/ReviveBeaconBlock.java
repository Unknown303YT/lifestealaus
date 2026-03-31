package com.riverstone.unknown303.lifestealaus.block.custom;

import com.mojang.serialization.MapCodec;
import com.riverstone.unknown303.lifestealaus.block.entity.custom.ReviveBeaconBlockEntity;
import com.riverstone.unknown303.lifestealaus.misc.ModStats;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Stainable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class ReviveBeaconBlock extends BlockWithEntity implements Stainable {
    public static final MapCodec<ReviveBeaconBlock> CODEC = createCodec(ReviveBeaconBlock::new);

    public ReviveBeaconBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient() || !(world.getBlockEntity(pos) instanceof ReviveBeaconBlockEntity blockEntity))
            return ActionResult.SUCCESS;

        player.openHandledScreen(blockEntity);
        player.incrementStat(ModStats.INTERACT_WITH_REVIVE_BEACON);

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReviveBeaconBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {};
    }

    @Override
    public MapCodec<ReviveBeaconBlock> getCodec() {
        return CODEC;
    }

    @Override
    public DyeColor getColor() {
        return DyeColor.RED;
    }
}
