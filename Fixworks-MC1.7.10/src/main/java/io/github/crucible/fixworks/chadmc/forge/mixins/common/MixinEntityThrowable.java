package io.github.crucible.fixworks.chadmc.forge.mixins.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import io.github.crucible.fixworks.chadmc.forge.implementation.IThrowableHook;

@Mixin(EntityThrowable.class)
public abstract class MixinEntityThrowable implements IThrowableHook {
    @Shadow
    private EntityLivingBase thrower;

    @Override
    public void setThrower(EntityLivingBase player) {
        this.thrower = player;
    }
}
