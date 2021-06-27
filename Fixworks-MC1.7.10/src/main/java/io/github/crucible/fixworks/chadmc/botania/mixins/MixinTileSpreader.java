package io.github.crucible.fixworks.chadmc.botania.mixins;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.github.crucible.fixworks.chadmc.forge.implementation.IThrowableHook;
import io.github.crucible.fixworks.chadmc.forge.implementation.ITileEntity;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.entity.EntityManaBurst;

@Mixin(value = TileSpreader.class, remap = false)
public abstract class MixinTileSpreader implements ITileEntity {

    @Redirect(method = "getBurst", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/entity/EntityManaBurst;setSourceLens(Lnet/minecraft/item/ItemStack;)V"))
    private void setSourceLensProxy(EntityManaBurst entity, ItemStack lens) {
        ((IThrowableHook) entity).setThrower(this.getFakePlayer());
        entity.setSourceLens(lens);
    }

}
