package io.github.crucible.fixworks.chadmc.enderio.mixins;

import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import crazypants.enderio.conduit.packet.PacketOCConduitSignalColor;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

@Mixin(value = PacketOCConduitSignalColor.class, remap = false)
public abstract class MixinPacketOCConduitSignalColor {
    @Inject(method = "onMessage", at = @At("HEAD"), cancellable = true)
    private void onMessage(PacketOCConduitSignalColor message, MessageContext ctx, CallbackInfoReturnable<IMessage> ci) {
        try {
            EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
            int x = this.getValue(message, "x");
            int y = this.getValue(message, "y");
            int z = this.getValue(message, "z");
            if (!EHIntegration.canBreak(entityPlayer, x, y, z)) {
                ci.setReturnValue(null);
            }
        } catch (Exception e) {
            ci.setReturnValue(null);
        }
    }

    private int getValue(Object target, String targetField) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(targetField);
        field.setAccessible(true);
        return (int) field.get(target);
    }
}
