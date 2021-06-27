package io.github.crucible.fixworks.incelmc.forge.mixins.common;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import io.github.crucible.fixworks.incelmc.forge.implementation.BetterCraftCache;

import javax.annotation.Nullable;

@Mixin(CraftingManager.class)
public abstract class MixinCraftingManager {

    /**
     * @author EverNife
     * @reason Cache NON_NBT Crafting Matrix by [Item and Meta],
     * to prevent dispendious time on large modpacks with thousands of craftings
     */
    @Nullable
    @Overwrite
    public static IRecipe findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn) {
        return BetterCraftCache.findMatchingRecipe(craftMatrix, worldIn);
    }

    /**
     * @author EverNife
     * @reason Cache NON_NBT Crafting Matrix by [Item and Meta],
     * to prevent dispendious time on large modpacks with thousands of craftings
     */
    @Overwrite
    public static NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftMatrix, World worldIn) {
        IRecipe iRecipe = BetterCraftCache.findMatchingRecipe(craftMatrix, worldIn);
        if (iRecipe != null)
            return iRecipe.getRemainingItems(craftMatrix);

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            nonnulllist.set(i, craftMatrix.getStackInSlot(i));
        }

        return nonnulllist;
    }
}