package io.github.crucible.fixworks.chadmc.thaumcraft.mixins;

import io.github.crucible.fixworks.chadmc.forge.implementation.FakePlayerManager;
import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.entities.monster.boss.EntityCultistPortal;

@Mixin(value = EntityCultistPortal.class, remap = false)
public abstract class MixinEntityCultistPortal {

    @Redirect(method = "func_70071_h_", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;func_147465_d(IIILnet/minecraft/block/Block;II)Z"))
    private boolean setBlockProxy(World world, int x, int y, int z, Block block, int i1, int i2) {
        if (!EHIntegration.canBreak(FakePlayerManager.get((WorldServer) world), x, y, z) || world.getTileEntity(x, y, z) != null)
            return false;
        else
            return world.setBlock(x, y, z, block, i1, i2);
    }
}
