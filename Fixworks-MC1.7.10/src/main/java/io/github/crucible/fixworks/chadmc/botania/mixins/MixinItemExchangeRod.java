package io.github.crucible.fixworks.chadmc.botania.mixins;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import vazkii.botania.common.item.rod.ItemExchangeRod;

@Mixin(value = ItemExchangeRod.class, remap = false)
public abstract class MixinItemExchangeRod {

    @Inject(method = "exchange", at = @At("HEAD"), cancellable = true)
    private void checkPermission(World world, EntityPlayer player, int x, int y, int z, ItemStack stack, Block blockToSet, int metaToSet, CallbackInfoReturnable<Boolean> ci) {
        if (world.blockExists(x, y, z) && !EHIntegration.canBreak(player, x, y, z)) {
            ci.setReturnValue(false);
            ci.cancel();
        }
    }
}
