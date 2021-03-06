package io.github.crucible.fixworks.chadmc.botania.mixins;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import vazkii.botania.common.item.rod.ItemSmeltRod;

@Mixin(value = ItemSmeltRod.class, remap = false)
public abstract class MixinItemSmeltRod {
    @Redirect(method = "onUsingTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;func_147465_d(IIILnet/minecraft/block/Block;II)Z"))
    private boolean setBlockProxy(World world, int x, int y, int z, Block block, int i1, int i2, ItemStack stack, EntityPlayer p, int time) {
        if (!EHIntegration.canBreak(p, x, y, z))
            return false;
        else
            return world.setBlock(x, y, z, block, i1, i2);
    }
}
