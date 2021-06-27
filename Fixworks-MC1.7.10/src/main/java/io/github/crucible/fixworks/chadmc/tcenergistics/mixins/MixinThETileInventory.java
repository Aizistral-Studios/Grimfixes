package io.github.crucible.fixworks.chadmc.tcenergistics.mixins;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thaumicenergistics.common.tiles.abstraction.ThETileInventory;

@Mixin(value = ThETileInventory.class)
public abstract class MixinThETileInventory {

    /**
     * @author EverNife
     * @reason Tentativa resolve o dupe;
     *
     * Testes em campo foram inconclusivos quantos a eficácia dessa mudança!
     *
     * \--> Removes from an inventory slot (first arg) up to a
     * specified number (second arg) of items and returns them
     * in a new stack.
     */
    @Overwrite
    public ItemStack decrStackSize(int slotIndex, int amount) {
        return null;
    }

}