package org.zeith.modid.init;


import net.minecraft.world.level.block.entity.BlockEntityType;
import org.zeith.hammerlib.annotations.*;
import org.zeith.hammerlib.api.forge.BlockAPI;
import org.zeith.modid.entity.custom.CatcherTile;

@SimplyRegister
public interface TilesMI {
    @RegistryName("catcher_tile")
    BlockEntityType<CatcherTile> CATCHER_TILE = BlockAPI.createBlockEntityType(CatcherTile::new, BlocksMI.catcherBlock);
}
