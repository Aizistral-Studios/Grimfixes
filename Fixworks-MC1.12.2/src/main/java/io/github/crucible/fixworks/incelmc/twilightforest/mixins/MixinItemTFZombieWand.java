package io.github.crucible.fixworks.incelmc.twilightforest.mixins;

import io.github.crucible.grimoire.mc1_12_2.api.integration.eventhelper.EHIntegration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.entity.EntityTFLoyalZombie;
import twilightforest.item.ItemTFZombieWand;
import twilightforest.util.EntityUtil;

import javax.annotation.Nonnull;

@Mixin(value = ItemTFZombieWand.class, remap = false)
public abstract class MixinItemTFZombieWand {

    /**
     * @author EverNife
     * @reason
     *
     * Fire break-event before spawning zombies
     */
    @Nonnull
    @Overwrite(remap = true)
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (stack.getItemDamage() == stack.getMaxDamage())
            return ActionResult.newResult(EnumActionResult.FAIL, stack);

        if (!world.isRemote) {
            // what block is the player pointing at?
            RayTraceResult ray = EntityUtil.rayTrace(player, 20.0);

            if (ray != null && ray.hitVec != null) {
                if (!EHIntegration.canBreak(player, ray.getBlockPos()))
                    return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
                EntityTFLoyalZombie zombie = new EntityTFLoyalZombie(world);
                zombie.setPositionAndRotation(ray.hitVec.x, ray.hitVec.y, ray.hitVec.z, 1.0F, 1.0F);
                zombie.setTamed(true);
                zombie.setOwnerId(player.getUniqueID());
                zombie.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 1));
                world.spawnEntity(zombie);

                stack.damageItem(1, player);
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }
}