package io.github.crucible.fixworks.chadmc.thaumcraft.mixins;

import io.github.crucible.fixworks.chadmc.forge.implementation.FakePlayerManager;
import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.entities.monster.boss.EntityEldritchGolem;

@Mixin(value = EntityEldritchGolem.class, remap = false)
public abstract class MixinEntityEldritchGolem {

    @Redirect(method = "func_70636_d", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;func_147480_a(IIIZ)Z"))
    private boolean breakBlockProxy(World world, int x, int y, int z, boolean slaOqSeriaIsso) {
        if (!EHIntegration.canBreak(FakePlayerManager.get((WorldServer) world), x, y, z)) return false;
        else return world.func_147480_a(x, y, z, slaOqSeriaIsso);
    }

}
