package org.zeith.modid.client.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zeith.modid.ModId;

import static org.zeith.modid.init.EntityMI.MAGIC_PROJECTILE;

@Mod.EventBusSubscriber(modid = ModId.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents
{
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(MAGIC_PROJECTILE, MagicProjectileRenderer::new);
    }
}
