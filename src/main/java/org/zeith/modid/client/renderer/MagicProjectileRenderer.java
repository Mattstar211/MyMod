package org.zeith.modid.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.zeith.modid.ModId;
import org.zeith.modid.entity.custom.MagicProjectile;

public class MagicProjectileRenderer extends EntityRenderer<MagicProjectile> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ModId.MOD_ID, "resources/assets/modid/textures/item/ball.png");

    protected MagicProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    public void render(MagicProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }
    @Override
    public ResourceLocation getTextureLocation(MagicProjectile magicProjectile) {
        return TEXTURE;
    }
}
