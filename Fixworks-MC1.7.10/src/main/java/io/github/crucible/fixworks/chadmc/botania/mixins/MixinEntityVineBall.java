package io.github.crucible.fixworks.chadmc.botania.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import vazkii.botania.common.entity.EntityVineBall;

@Mixin(value = EntityVineBall.class, remap = false)
public abstract class MixinEntityVineBall extends EntityThrowable {
    public MixinEntityVineBall(World world) {
        super(world);
    }

    @Inject(method = "func_70184_a", at = @At("HEAD"), cancellable = true)
    private void checkPermission(MovingObjectPosition pos, CallbackInfo ci) {
        if (this.getThrower() instanceof EntityPlayer) {
            if (!EHIntegration.canBreak((EntityPlayer) this.getThrower(), pos.blockX, pos.blockY, pos.blockZ)) {
                this.setDead();
                ci.cancel();
            }
        }
    }

}
