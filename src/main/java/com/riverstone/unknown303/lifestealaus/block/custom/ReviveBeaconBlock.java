package com.riverstone.unknown303.lifestealaus.block.custom;

import com.mojang.authlib.GameProfile;
import com.riverstone.unknown303.lifestealaus.data.HeartData;
import com.riverstone.unknown303.lifestealaus.misc.ModStats;
import com.riverstone.unknown303.lifestealaus.screen.ReviveScreenHandler;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ReviveBeaconBlock extends BeaconBlock {
    public ReviveBeaconBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient())
            return ActionResult.SUCCESS;

        ServerWorld serverWorld = (ServerWorld) world;
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        HeartData data = HeartData.get(serverWorld);

        List<UUID> banned = data.getBannedPlayers();

        int size = 9 * 4;
        SimpleInventory inv = new SimpleInventory(size);

        for (int slot = 0; slot < size; slot++) {
            if (slot >= banned.size()) {
                ItemStack emptyStack = new ItemStack(Items.GRAY_STAINED_GLASS_PANE);
                emptyStack.set(DataComponentTypes.ITEM_NAME, Text.literal(""));
                inv.setStack(slot, emptyStack);
                continue;
            }

            UUID uuid = banned.get(slot);
            GameProfile profile = serverWorld.getServer().getApiServices()
                    .profileResolver().getProfileById(uuid).orElseThrow();

            ItemStack head = new ItemStack(Items.PLAYER_HEAD);

            head.set(DataComponentTypes.PROFILE, ProfileComponent.ofStatic(profile));
            head.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Revive " + profile.name()).withColor(0xFFFFFF));

            inv.setStack(slot, head);
        }

        player.openHandledScreen(
                new SimpleNamedScreenHandlerFactory(
                        (syncId, playerInventory, player1) ->
                                new ReviveScreenHandler(syncId, playerInventory, inv, pos, serverWorld),
                        Text.translatable("container.lifestealaus.revive")
                )
        );
        player.incrementStat(ModStats.INTERACT_WITH_REVIVE_BEACON);

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return super.createBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {};
    }
}
