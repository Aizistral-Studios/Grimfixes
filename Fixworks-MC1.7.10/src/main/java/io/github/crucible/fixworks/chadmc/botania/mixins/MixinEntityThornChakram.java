package io.github.crucible.fixworks.chadmc.botania.mixins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import vazkii.botania.common.entity.EntityThornChakram;

@Mixin(value = EntityThornChakram.class, remap = false)
public abstract class MixinEntityThornChakram extends EntityThrowable {

    public MixinEntityThornChakram(World world) {
        super(world);
    }

    @Inject(method = "func_70184_a", at = @At("HEAD"), cancellable = true)
    private void checkPermission(MovingObjectPosition pos, CallbackInfo ci) {
        if (this.getThrower() instanceof EntityPlayer && pos.entityHit instanceof EntityLivingBase && !EHIntegration.canDamage(this.getThrower(), pos.entityHit)) {
            this.setDead();
            ci.cancel();
        }

    }

}