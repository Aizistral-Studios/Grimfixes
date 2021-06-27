package io.github.crucible.fixworks.incelmc.armourers.mixins.network;

import moe.plushie.armourers_workshop.common.network.messages.client.MessageClientGuiUpdateMannequin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import io.github.crucible.fixworks.incelmc.armourers.implementation.IHasID;

@Mixin(value = MessageClientGuiUpdateMannequin.class, remap = false)
public abstract class MixinMessageClientGuiUpdateMannequin implements IHasID {

    @Shadow
    private int id;

    @Override
    public int getID() {
        return this.id;
    }

}
