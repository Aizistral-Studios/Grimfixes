package io.github.crucible.fixworks.incelmc.botania.mixins.rods;

import io.github.crucible.grimoire.mc1_12_2.api.integration.eventhelper.EHIntegration;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.item.rod.ItemExchangeRod;

@Mixin(value = ItemExchangeRod.class, remap = false)
public abstract class MixinItemExchangeRod {

    /**
     * @author EverNife
     * @reason
     *
     * Add EventHelper Check
     */
    @Inject(method = "exchange", at = @At("HEAD"), require = 1, cancellable = true)
    public void exchange(World world, EntityPlayer player, BlockPos pos, ItemStack stack, IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!EHIntegration.canBreak(player, pos)) {
            cir.setReturnValue(false);
        }
    }
}