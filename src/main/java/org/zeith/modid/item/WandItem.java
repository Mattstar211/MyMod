package org.zeith.modid.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.zeith.modid.entity.custom.MagicProjectile;

import static org.zeith.modid.init.EntityMI.MAGIC_PROJECTILE;

public class WandItem extends Item {
    public WandItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        if (!level.isClientSide) {
            Vec3 direction = player.getLookAngle().normalize();
            MagicProjectile ball = new MagicProjectile(MAGIC_PROJECTILE, level);
            ball.setPos(player.getX(), player.getY() + 1.5, player.getZ());
            ball.shoot(direction.x, direction.y, direction.z, 1.0f, 0.0f);
            level.addFreshEntity(ball);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
