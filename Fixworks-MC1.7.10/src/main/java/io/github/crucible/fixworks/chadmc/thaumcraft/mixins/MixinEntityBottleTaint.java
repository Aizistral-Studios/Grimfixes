package io.github.crucible.fixworks.chadmc.thaumcraft.mixins;

import io.github.crucible.fixworks.chadmc.forge.implementation.FakePlayerManager;
import io.github.crucible.fixworks.chadmc.forge.implementation.IThrowableHook;
import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.common.entities.projectile.EntityBottleTaint;
import thaumcraft.common.lib.utils.Utils;

@Mixin(value = EntityBottleTaint.class, remap = false)
public abstract class MixinEntityBottleTaint extends EntityThrowable implements IThrowableHook {
    public MixinEntityBottleTaint(World world) {
        super(world);
    }

    @Inject(method = "func_70184_a", at = @At("HEAD"), cancellable = true)
    private void checkPermission(MovingObjectPosition pos, CallbackInfo ci) {
        if (this.getThrower() == null) {
            this.setThrower(FakePlayerManager.get((WorldServer) this.worldObj));
        }
        if (this.getThrower() instanceof EntityPlayer) {
            if (!EHIntegration.canBreak((EntityPlayer) this.getThrower(), pos.blockX, pos.blockY, pos.blockZ)) {
                this.setDead();
                ci.cancel();
            }
        } else {
            this.setDead();
            ci.cancel();
        }
    }

    @Redirect(method = "func_70184_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;func_147465_d(IIILnet/minecraft/block/Block;II)Z"))
    private boolean setBlockProxy(World world, int x, int y, int z, Block block, int i, int i2, MovingObjectPosition pos) {
        if (this.getThrower() instanceof EntityPlayer && !EHIntegration.canBreak((EntityPlayer) this.getThrower(), x, y, z))
            return false;
        else
            return world.setBlock(x, y, z, block, i, i2);
    }

    @Redirect(method = "func_70184_a", at = @At(value = "INVOKE", target = "Lthaumcraft/common/lib/utils/Utils;setBiomeAt(Lnet/minecraft/world/World;IILnet/minecraft/world/biome/BiomeGenBase;)V"))
    private void setBiomeAtProxy(World world, int x, int z, BiomeGenBase biome, MovingObjectPosition pos) {
        if (!(this.getThrower() instanceof EntityPlayer) || !!EHIntegration.canBreak((EntityPlayer) this.getThrower(), x, pos.blockY, z)) {
            Utils.setBiomeAt(world, x, z, biome);
        }
    }
}
