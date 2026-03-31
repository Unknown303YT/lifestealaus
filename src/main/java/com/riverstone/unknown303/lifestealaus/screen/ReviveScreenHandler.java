package com.riverstone.unknown303.lifestealaus.screen;

import com.riverstone.unknown303.lifestealaus.data.HeartData;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class ReviveScreenHandler extends GenericContainerScreenHandler {
    private final SimpleInventory inventory;
    private final BlockPos beaconPos;
    private final ServerWorld world;

    public ReviveScreenHandler(int syncId, PlayerInventory playerInventory,
                               SimpleInventory inventory, BlockPos beaconPos, ServerWorld world) {
        super(ScreenHandlerType.GENERIC_9X4, syncId, playerInventory, inventory, 4);
        this.inventory = inventory;
        this.beaconPos = beaconPos;
        this.world = world;
    }

    @Override
    public void onSlotClick(int slot, int button, SlotActionType actionType, PlayerEntity player) {
        if (slot < inventory.size()) {
            ItemStack clicked = inventory.getStack(slot);

            if (clicked.getItem() == Items.PLAYER_HEAD) {
                UUID playerUUID = clicked.get(DataComponentTypes.PROFILE).getGameProfile().id();
                HeartData.get(world).revive(playerUUID);
                world.removeBlock(beaconPos, false);
                if (player instanceof ServerPlayerEntity serverPlayer)
                    serverPlayer.closeHandledScreen();
            }
        }
    }
}
