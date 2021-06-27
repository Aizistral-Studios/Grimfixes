package io.github.crucible.fixworks.chadmc.botania.mixins;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import vazkii.botania.common.entity.EntityEnderAirBottle;

@Mixin(value = EntityEnderAirBottle.class, remap = false)
public abstract class MixinEntityEnderAirBottle extends EntityThrowable {
    public MixinEntityEnderAirBottle(World world) {
        super(world);
    }

    /**
     * @author juanmuscaria
     * @reason Desativa o impacto da garrafa.
     */
    @Override
    @Overwrite(remap = true)
    protected void onImpact(MovingObjectPosition pos) {
        this.setDead();
    }

}