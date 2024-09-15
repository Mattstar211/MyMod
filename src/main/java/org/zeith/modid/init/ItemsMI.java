package org.zeith.modid.init;

import net.minecraft.world.item.Item;
import org.zeith.hammerlib.annotations.RegistryName;
import org.zeith.hammerlib.annotations.SimplyRegister;
import org.zeith.modid.ModId;
import org.zeith.modid.item.WandItem;



@SimplyRegister
public interface ItemsMI
{
	@RegistryName("wand")
	Item WAND = ModId.MOD_TAB.add(new WandItem(new Item.Properties()));
}
