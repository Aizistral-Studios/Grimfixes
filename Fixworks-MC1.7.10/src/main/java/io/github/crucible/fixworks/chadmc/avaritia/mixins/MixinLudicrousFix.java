package io.github.crucible.fixworks.chadmc.avaritia.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

import fox.spiteful.avaritia.entity.EntityGapingVoid;

@Mixin(EntityGapingVoid.class)
public abstract class MixinLudicrousFix extends Entity {

    public MixinLudicrousFix(World world) {
        super(world);
    }

    /**
     * @author juanmuscaria
     * @reason Desabilitar o buraco negro do avaritia.
     */
    @Override
    @Overwrite
    public void onUpdate() {
        super.onUpdate();
        this.setDead(); // Nope, quem achou que um buraco negro portátil era uma boa ideia?
    }
}
