package org.zeith.modid.init;

import org.zeith.hammerlib.annotations.*;
import org.zeith.modid.block.CatcherBlock;

@SimplyRegister
public interface BlocksMI
{
    @RegistryName("catcher_block")
    CatcherBlock catcherBlock = new CatcherBlock();
}