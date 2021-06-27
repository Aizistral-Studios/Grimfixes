package io.github.crucible.fixworks.chadmc.tcenergistics.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thaumicenergistics.common.container.ContainerPartArcaneCraftingTerminal;

@Mixin(value = ContainerPartArcaneCraftingTerminal.class)
public abstract class MixinContainerPartAcraneCraftingTerminal {

    /**
     * @author EverNife
     * @reason There are some dupes caused by the SHIFT-CLICK,
     * better justa disable it...
     */
    @Overwrite
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        return null;
    }
}