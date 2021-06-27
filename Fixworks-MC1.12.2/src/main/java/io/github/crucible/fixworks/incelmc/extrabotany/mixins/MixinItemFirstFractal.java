package io.github.crucible.fixworks.incelmc.extrabotany.mixins;

import com.meteor.extrabotany.common.item.relic.ItemFirstFractal;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemFirstFractal.class, remap = false)
public abstract class MixinItemFirstFractal {

    /**
     * @author EverNife
     * @reason
     *
     * This should only be execute on the ClientSide
     */

    @Inject(method = "leftClick(Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$LeftClickEmpty;)V", at = @At("HEAD"), cancellable = true)
    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt, CallbackInfo info) {
        if (!evt.getWorld().isRemote) {
            info.cancel();
        }
    }

    /**
     * @author EverNife
     * @reason
     *
     * This should only be execute on the ClientSide
     */

    @Inject(method = "leftClick(Lnet/minecraftforge/event/entity/player/PlayerInteractEvent$LeftClickBlock;)V", at = @At("HEAD"), cancellable = true)
    public void leftClick(PlayerInteractEvent.LeftClickBlock evt, CallbackInfo info) {
        if (!evt.getWorld().isRemote) {
            info.cancel();
        }
    }
}