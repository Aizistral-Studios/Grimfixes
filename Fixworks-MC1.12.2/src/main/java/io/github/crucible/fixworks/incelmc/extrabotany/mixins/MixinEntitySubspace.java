package io.github.crucible.fixworks.incelmc.extrabotany.mixins;

import com.meteor.extrabotany.api.ExtraBotanyAPI;
import com.meteor.extrabotany.common.entity.EntitySubspace;
import com.meteor.extrabotany.common.entity.EntitySubspaceSpear;
import com.meteor.extrabotany.common.entity.EntityThrowableCopy;
import com.meteor.extrabotany.common.entity.gaia.EntityVoidHerrscher;
import com.meteor.extrabotany.common.item.ModItems;
import com.meteor.extrabotany.common.item.relic.ItemExcaliber;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import vazkii.botania.common.entity.EntityManaBurst;

@Mixin(value = EntitySubspace.class, remap = false)
public abstract class MixinEntitySubspace extends EntityThrowableCopy {

    @Shadow
    public abstract int getLiveTicks();

    @Shadow
    public abstract int getInterval();

    @Shadow
    public abstract int getCount();

    @Shadow
    public abstract int getDelay();

    @Shadow
    public abstract void setCount(int delay);

    @Shadow
    public abstract int getType();

    public MixinEntitySubspace(String s) {
        super(null);
        throw new RuntimeException("This should never Run!");
    }

    /**
     * @author EverNife
     * @reason
     *
     * This should only be execute on the ClientSide
     */
    @Override
    @Overwrite(remap = true)
    public void onUpdate() {
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;

        super.onUpdate();

        if (this.ticksExisted < this.getDelay())
            return;

        if (this.ticksExisted > this.getLiveTicks() + this.getDelay()) {
            this.setDead();
        }
        EntityLivingBase thrower = this.getThrower();
        if (!this.world.isRemote && (thrower == null || thrower.isDead)) {
            this.setDead();
            return;
        }

        if (!this.world.isRemote)
            if (this.getType() == 0) {
                if (this.ticksExisted % this.getInterval() == 0 && this.getCount() < 5 && this.ticksExisted > this.getDelay() + 5 && this.ticksExisted < this.getLiveTicks() - this.getDelay() - 10) {
                    if (!(thrower instanceof EntityPlayer)) {
                        this.setDead();
                    }
                    EntityPlayer player = (EntityPlayer) this.getThrower();
                    if (ExtraBotanyAPI.cantAttack(player, player)) {
                        this.setDead();
                    }
                    EntityManaBurst burst = ItemExcaliber.getBurst(player, new ItemStack(ModItems.excaliber));
                    burst.setPosition(this.posX, this.posY, this.posZ);
                    burst.setColor(0XFFAF00);
                    player.world.spawnEntity(burst);
                    this.setCount(this.getCount() + 1);
                }
            } else if (this.getType() == 1) {
                if (this.ticksExisted > this.getDelay() + 8 && this.getCount() < 1) {
                    EntitySubspaceSpear spear = new EntitySubspaceSpear(this.world, thrower);
                    spear.setDamage(12);
                    if (thrower instanceof EntityVoidHerrscher) {
                        spear.setDamage(14);
                        spear.setLiveTicks(1);
                    }
                    spear.setLife(100);
                    spear.rotationYaw = thrower.rotationYaw;
                    spear.setPitch(-thrower.rotationPitch);
                    spear.setRotation(MathHelper.wrapDegrees(-thrower.rotationYaw + 180));
                    spear.shoot(thrower, thrower.rotationPitch, thrower.rotationYaw, 0.0F, 2.45F, 1.0F);
                    spear.setPosition(this.posX, this.posY - 0.75F, this.posZ);
                    thrower.world.spawnEntity(spear);
                    this.setCount(this.getCount() + 1);
                }
            } else if (this.getType() == 2) {
                if (this.ticksExisted % this.getInterval() == 0 && this.getCount() < 6 && this.ticksExisted > this.getDelay() + 5 && this.ticksExisted < this.getLiveTicks() - this.getDelay() - 10) {
                    if (!(thrower instanceof EntityVoidHerrscher)) {
                        this.setDead();
                    }
                    EntityVoidHerrscher herr = (EntityVoidHerrscher) this.getThrower();
                    if (herr.getPlayersAround().isEmpty()) {
                        this.setDead();
                        //[GRIMOIRE START]
                        return;
                        //[GRIMOIRE END]
                    }
                    if (ExtraBotanyAPI.cantAttack(thrower, herr.getPlayersAround().get(0))) {
                        this.setDead();
                    }
                    EntityManaBurst burst = ItemExcaliber.getBurst(herr.getPlayersAround().get(0), new ItemStack(ModItems.excaliber));
                    burst.setPosition(this.posX, this.posY, this.posZ);
                    burst.setColor(0XFFD700);
                    burst.shoot(thrower, thrower.rotationPitch + 15F, thrower.rotationYaw, 0F, 1F, 0F);
                    thrower.world.spawnEntity(burst);
                    this.setCount(this.getCount() + 1);
                }
            }
    }

}