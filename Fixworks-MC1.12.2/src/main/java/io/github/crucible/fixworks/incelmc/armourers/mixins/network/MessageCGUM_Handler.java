package io.github.crucible.fixworks.incelmc.armourers.mixins.network;

import io.github.crucible.grimoire.mc1_12_2.api.integration.eventhelper.EHIntegration;

import io.github.crucible.fixworks.incelmc.armourers.implementation.IHasID;
import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiUpdateMannequin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MessageClientGuiUpdateMannequin.Handler.class, remap = false)
public abstract class MessageCGUM_Handler {

    /**
     * @author EverNife
     * @reason Fire a break-event before opening a manequin!
     *
     * This could be done in any other place, but why not use this event handler?
     */
    @Inject(method = {"onMessage"}, at = @At("HEAD"), cancellable = true)
    public void onMessage(MessageClientGuiUpdateMannequin message, MessageContext ctx, CallbackInfoReturnable<IMessage> cir) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        World world = player.getEntityWorld();
        Entity entity = world.getEntityByID(((IHasID)message).getID());
        if (entity != null){
            if (!EHIntegration.canBreak(player, entity.getPosition())){
                cir.setReturnValue(null);
            }
        }
    }

}
