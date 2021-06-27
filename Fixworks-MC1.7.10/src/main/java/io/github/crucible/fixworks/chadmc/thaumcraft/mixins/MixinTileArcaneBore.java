package io.github.crucible.fixworks.chadmc.thaumcraft.mixins;

import io.github.crucible.fixworks.chadmc.forge.implementation.ITileEntity;
import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.common.tiles.TileArcaneBore;

@Mixin(value = TileArcaneBore.class, remap = false)
public abstract class MixinTileArcaneBore implements ITileEntity {

    @Shadow
    int digX;

    @Shadow
    int digY;

    @Shadow
    int digZ;

    @Inject(method = "dig", at = @At("HEAD"), cancellable = true)
    private void checkPermission(CallbackInfo ci) {
        if (!EHIntegration.canBreak(this.getFakePlayer(), this.digX, this.digY, this.digZ)) {
            ci.cancel();
        }
    }
}
