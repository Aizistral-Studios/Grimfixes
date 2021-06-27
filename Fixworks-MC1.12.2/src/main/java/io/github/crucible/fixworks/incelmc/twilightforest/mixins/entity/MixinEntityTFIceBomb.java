package io.github.crucible.fixworks.incelmc.twilightforest.mixins.entity;

import io.github.crucible.grimoire.mc1_12_2.api.integration.eventhelper.EHIntegration;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.entity.EntityTFThrowable;
import twilightforest.entity.boss.EntityTFIceBomb;

@Mixin(value = EntityTFIceBomb.class, remap = false)
public abstract class MixinEntityTFIceBomb extends EntityTFThrowable {

    public MixinEntityTFIceBomb(String someRandom) {
        super(null, null);
        throw new RuntimeException("This should never run!");
    }

    /**
     * @author EverNife
     * @reason
     *
     * Only allow real players to throw ice bombs
     * Fire place-event before placing bomb on the ground
     */
    @Overwrite
    private void doTerrainEffect(BlockPos pos) {

        if (this.thrower == null || !(this.thrower instanceof EntityPlayerMP) || this.thrower instanceof FakePlayer)
            return;

        EntityPlayerMP playerMP = (EntityPlayerMP) this.thrower;
        if (!EHIntegration.canBreak(playerMP, pos))
            return;

        IBlockState state = this.world.getBlockState(pos);
        if (state.getMaterial() == Material.WATER) {
            this.world.setBlockState(pos, Blocks.ICE.getDefaultState());
        }
        if (state.getMaterial() == Material.LAVA) {
            this.world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
        }
        if (this.world.isAirBlock(pos) && Blocks.SNOW_LAYER.canPlaceBlockAt(this.world, pos)) {
            this.world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState());
        }
    }

}