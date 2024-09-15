package org.zeith.modid.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class MagicProjectile extends ThrowableItemProjectile {

    private final double initialSpeed;

    public MagicProjectile(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
        this.initialSpeed = 1.0;
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        double x = pResult.getEntity().getX();
        double y = pResult.getEntity().getY();
        double z = pResult.getEntity().getZ();

        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
        lightningBolt.moveTo(x, y, z);
        this.level().addFreshEntity(lightningBolt);

        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        double x = pResult.getBlockPos().getX();
        double y = pResult.getBlockPos().getY();
        double z = pResult.getBlockPos().getZ();

        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
        lightningBolt.moveTo(x, y, z);
        this.level().addFreshEntity(lightningBolt);

        this.discard();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            Vec3 currentSpeed = this.getDeltaMovement();
            double speed = Math.sqrt(currentSpeed.x * currentSpeed.x + currentSpeed.y * currentSpeed.y + currentSpeed.z * currentSpeed.z);
            if (speed > 0) {
                Vec3 normalizedSpeed = currentSpeed.normalize().scale(this.initialSpeed);
                this.setDeltaMovement(normalizedSpeed);
            }
        }

        if (this.level().isClientSide) {
            addParticles();
        }
    }

    private void addParticles() {
        for (int i = 0; i < 10; i++) {
            double xOffset = random.nextDouble() * 0.2 - 0.1;
            double yOffset = random.nextDouble() * 0.2 - 0.1;
            double zOffset = random.nextDouble() * 0.2 - 0.1;
            this.level().addParticle(ParticleTypes.END_ROD,
                    getX() + xOffset,
                    getY() + yOffset,
                    getZ() + zOffset,
                    0, 0, 0);
        }
    }
}
