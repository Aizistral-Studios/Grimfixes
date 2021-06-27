package io.github.crucible.fixworks.chadmc.botania.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.item.rod.ItemGravityRod;

@Mixin(value = ItemGravityRod.class, remap = false)
public abstract class MixinItemGravityRod {

    @Redirect(method = "func_77659_a", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/item/rod/ItemGravityRod;setEntityMotionFromVector(Lnet/minecraft/entity/Entity;Lvazkii/botania/common/core/helper/Vector3;F)V"))
    private void setEntityMotionFromVectorProxy(Entity entity, Vector3 originalPosVector, float modifier, ItemStack stack, World world, EntityPlayer player) {
        if (!EHIntegration.canDamage(player, entity))
            return;

        ItemGravityRod.setEntityMotionFromVector(entity, originalPosVector, modifier);
    }

}
