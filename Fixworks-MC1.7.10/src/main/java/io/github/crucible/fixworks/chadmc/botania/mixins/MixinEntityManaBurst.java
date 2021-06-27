package io.github.crucible.fixworks.chadmc.botania.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.crucible.fixworks.chadmc.forge.implementation.IThrowableHook;
import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import vazkii.botania.common.entity.EntityManaBurst;


@Mixin(value = EntityManaBurst.class, remap = false)
public abstract class MixinEntityManaBurst extends EntityThrowable implements IThrowableHook {

    public MixinEntityManaBurst(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/EntityPlayer;)V", at = @At("RETURN"))
    private void onConstruct(EntityPlayer player, CallbackInfo ci) {
        this.setThrower(player);
    }

    @Inject(method = "func_70184_a", at = @At("HEAD"), cancellable = true)
    private void checkPermission(MovingObjectPosition pos, CallbackInfo ci) {
        if (this.getThrower() instanceof EntityPlayer) {
            if (!EHIntegration.canBreak((EntityPlayer) this.getThrower(), pos.blockX, pos.blockY, pos.blockZ)) {
                this.setDead();
                ci.cancel();
            }
        } else {
            this.setDead();
            ci.cancel();
        }
    }

}
