package io.github.crucible.fixworks.incelmc.twilightforest.mixins.entity;

import io.github.crucible.grimoire.mc1_12_2.api.integration.eventhelper.EHIntegration;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.block.TFBlocks;
import twilightforest.entity.EntityTFMoonwormShot;
import twilightforest.entity.EntityTFThrowable;

@Mixin(value = EntityTFMoonwormShot.class, remap = false)
public abstract class MixinEntityTFMoonwormShot extends EntityTFThrowable {

    public MixinEntityTFMoonwormShot(String someRandom) {
        super(null, null);
        throw new RuntimeException("This should never run!");
    }

    /**
     * @author EverNife
     * @reason
     *
     * Only allow real players to place worms on the ground
     * Fire place-event before placing worm on the ground
     */
    @Override
    @Overwrite(remap = true)
    protected void onImpact(RayTraceResult ray) {
        if (!this.world.isRemote) {

            if (this.thrower == null || !(this.thrower instanceof EntityPlayerMP) || this.thrower instanceof FakePlayer)
                return;

            if (ray.typeOfHit == RayTraceResult.Type.BLOCK) {

                BlockPos pos = ray.getBlockPos().offset(ray.sideHit);
                IBlockState currentState = this.world.getBlockState(pos);

                if (!EHIntegration.canBreak((EntityPlayer) this.thrower, pos))
                    return;

                if (currentState.getBlock().isReplaceable(this.world, pos)) {
                    this.world.setBlockState(pos, TFBlocks.moonworm.getDefaultState().withProperty(BlockDirectional.FACING, ray.sideHit));
                    // to-do sound
                }
            }

            if (ray.entityHit != null) {
                ray.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
            }

            this.world.setEntityState(this, (byte) 3);
            this.setDead();
        }
    }

}