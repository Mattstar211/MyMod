package org.zeith.modid.init;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.zeith.hammerlib.annotations.RegistryName;
import org.zeith.hammerlib.annotations.SimplyRegister;
import org.zeith.modid.entity.custom.MagicProjectile;

@SimplyRegister
public interface EntityMI {


    @RegistryName("ball")
    EntityType<MagicProjectile> MAGIC_PROJECTILE = EntityType.Builder.<MagicProjectile>of(MagicProjectile::new, MobCategory.MISC)
            .sized(0.5f, 0.5f)
            .build("ball");
}
