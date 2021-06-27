package io.github.crucible.fixworks.chadmc.mekanism.mixins;

import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import mekanism.common.entity.EntityFlame;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityFlame.class, remap = false)
public abstract class MixinFlamethrower {

    @Shadow
    public Entity owner;

    @Inject(method = "burn", at = @At("HEAD"), cancellable = true)
    private void checkPermission(Entity entity, CallbackInfo ci) {
        if (!EHIntegration.canDamage(this.owner, entity)) {
            ci.cancel();
        }
    }

}