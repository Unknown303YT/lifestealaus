package com.riverstone.unknown303.lifestealaus.block.entity.custom;

import com.mojang.authlib.GameProfile;
import com.riverstone.unknown303.lifestealaus.block.entity.ModBlockEntities;
import com.riverstone.unknown303.lifestealaus.data.HeartData;
import com.riverstone.unknown303.lifestealaus.screen.ReviveScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeamEmitter;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ReviveBeaconBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Nameable, BeamEmitter {
    public ReviveBeaconBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REVIVE_BEACON, pos, state);
    }

    @Override
    public List<BeamSegment> getBeamSegments() {
        return List.of(new BeamSegment(0xFF5555));
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.REVIVE_BEACON;
    }

    @Override
    public Text getName() {
        return Text.translatable("container.lifestealaus.revive");
    }

    @Override
    public Text getDisplayName() {
        return getName();
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        ServerWorld serverWorld = (ServerWorld) world;

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
                    .profileResolver().getProfileById(uuid).orElse(null);

            ItemStack head = new ItemStack(Items.PLAYER_HEAD);

            if (profile != null) {
                head.set(DataComponentTypes.PROFILE, ProfileComponent.ofStatic(profile));
                head.set(DataComponentTypes.ITEM_NAME, Text.literal("Revive " + profile.name()).withColor(0xFFFFFF));
            } else {
                head.set(DataComponentTypes.PROFILE, ProfileComponent.ofDynamic(uuid));
                head.set(DataComponentTypes.ITEM_NAME, Text.literal("Revive Unknown Player").withColor(0xFFFFFF));
            }

            inv.setStack(slot, head);
        }

        return new ReviveScreenHandler(syncId, playerInventory, inv, this.getPos(), serverWorld);
    }
}
