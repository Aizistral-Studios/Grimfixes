package io.github.crucible.fixworks.chadmc.mekanism.implementation;

import net.minecraft.item.ItemStack;

public class ItemRecipeResult {
    private final ItemStack itemStack;

    public ItemRecipeResult(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack copyItemStack() {
        return this.itemStack == null ? null : this.itemStack.copy();
    }
}
