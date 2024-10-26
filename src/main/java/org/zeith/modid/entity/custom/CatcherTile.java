package org.zeith.modid.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zeith.hammerlib.tiles.TileSyncableTickable;
import org.zeith.modid.init.TilesMI;

import java.util.List;


public class CatcherTile extends TileSyncableTickable {
    private final EnergyStorage energyStorage = new EnergyStorage(16000);
    private static final Capability<IEnergyStorage> ENERGY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    public CatcherTile(BlockPos pos, BlockState state)
    {
        super(TilesMI.CATCHER_TILE, pos, state);
    }

    @Override
    public void update() {
        super.update();
        if (!level.isClientSide) {
            int radius = 8;
            List<LightningBolt> lightningBolts = level.getEntitiesOfClass(LightningBolt.class, new net.minecraft.world.phys.AABB(
                    getBlockPos().offset(-radius, -radius, -radius),
                    getBlockPos().offset(radius, radius, radius))
            );
            if (!lightningBolts.isEmpty()) {
                for (LightningBolt bolt : lightningBolts) {
                    if (!bolt.getTags().contains("processed")) {
                        double distance = Math.sqrt(bolt.blockPosition().distSqr(getBlockPos()));
                        if (distance <= radius) {
                            int energyToAdd = (int) ((1.0 - (distance / radius)) * 16000);
                            addEnergyToBlock(level, energyToAdd);
                            bolt.addTag("processed");
                        }
                    }
                }
            }
            if (energyStorage.getEnergyStored() > 0) distributeEnergyToNeighbors(level, getBlockPos());
        }
    }

    private void distributeEnergyToNeighbors(Level level, BlockPos pos) {
        int energyToDistribute = 100;
        int radius = 1;
        BlockPos.betweenClosedStream(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius))
                .forEach(neighborPos -> {
                    BlockEntity neighborEntity = level.getBlockEntity(neighborPos);
                    if (neighborEntity != null) {
                        LazyOptional<IEnergyStorage> neighborEnergyCap = neighborEntity.getCapability(ENERGY_CAPABILITY);
                        neighborEnergyCap.ifPresent(neighborEnergy -> {
                            if (neighborEnergy.canReceive()) {
                                int energyExtracted = energyStorage.extractEnergy(energyToDistribute, false);
                                int energyAccepted = neighborEnergy.receiveEnergy(energyExtracted, false);
                            }
                        });
                    }
                });
    }
    private void addEnergyToBlock(Level level, int energy) {
        if (energyStorage.getEnergyStored() + energy <= energyStorage.getMaxEnergyStored()) {
            energyStorage.receiveEnergy(energy, false);
            sendMessageToPlayers(level, "Добавлено " + energy + "FE   Текущая энергия: " + energyStorage.getEnergyStored() + "FE");
        } else {
            sendMessageToPlayers(level, "CatcherBlock полностью заряжен.");
        }
    }

    public void sendMessageToPlayers(Level level, String message) {
        for (Player player : level.getEntitiesOfClass(Player.class, new net.minecraft.world.phys.AABB(
                level.getMinBuildHeight(), 0, level.getMinBuildHeight(), level.getMaxBuildHeight(), 256, level.getMaxBuildHeight()))) {

            if (player instanceof ServerPlayer) {
                (player).sendSystemMessage(Component.literal(message));
            }
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ENERGY_CAPABILITY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energy.invalidate();
    }
}
