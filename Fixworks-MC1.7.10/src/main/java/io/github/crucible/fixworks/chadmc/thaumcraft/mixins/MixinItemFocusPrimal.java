package io.github.crucible.fixworks.chadmc.thaumcraft.mixins;

import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.common.items.wands.foci.ItemFocusPrimal;

@Mixin(value = ItemFocusPrimal.class, remap = false)
public abstract class MixinItemFocusPrimal {

    @Inject(method = "onFocusRightClick", at = @At("HEAD"), cancellable = true)
    private void onFocusRightClick(ItemStack itemstack, World world, EntityPlayer player, MovingObjectPosition mop, CallbackInfoReturnable<ItemStack> ci) {
        if (!EHIntegration.hasPermission(player, "feather.thaumcraft.focus.primal")) {
            player.addChatMessage(new ChatComponentTranslation("servertext.focus.permission"));
            ci.setReturnValue(itemstack);
            ci.cancel();
        }
    }
}
