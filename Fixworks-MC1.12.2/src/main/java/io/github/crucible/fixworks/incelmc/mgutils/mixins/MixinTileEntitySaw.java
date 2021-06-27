package io.github.crucible.fixworks.incelmc.mgutils.mixins;

import mob_grinding_utils.blocks.BlockSaw;
import mob_grinding_utils.tile.TileEntityInventoryHelper;
import mob_grinding_utils.tile.TileEntitySaw;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = TileEntitySaw.class, remap = false)
public abstract class MixinTileEntitySaw extends TileEntityInventoryHelper {

    @Shadow
    public boolean active;

    @Shadow
    public int animationTicks;

    @Shadow
    public int prevAnimationTicks;

    @Shadow
    @Override
    public abstract boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState);

    @Shadow
    protected abstract Entity activateBlock();

    public MixinTileEntitySaw(int invtSize) {
        super(invtSize);
    }

    /**
     * @author EverNife
     * @reason
     *
     * Apply fix to this: https://github.com/vadis365/Mob-Grinding-Utils/issues/126
     *
     * from this commit:
     * https://github.com/vadis365/Mob-Grinding-Utils/commit/2811f8703a5afa5e946a77b117c8db116da1ed04#diff-81474a6e4fe3b33ff3aa484fada7dcbbc0c6ad3d51358407fda1a01eec1a3c09L61
     *
     */
    @Overwrite(remap = true)
    public void update() {
        if (this.getWorld().isRemote && this.active) {
            this.prevAnimationTicks = this.animationTicks;
            if (this.animationTicks < 360) {
                this.animationTicks += 18;
            }
            if (this.animationTicks >= 360) {
                this.animationTicks -= 360;
                this.prevAnimationTicks -= 360;
            }
        }

        if (this.getWorld().isRemote && !this.active) {
            this.prevAnimationTicks = this.animationTicks = 0;
        }

        if (!this.getWorld().isRemote && this.getWorld().getTotalWorldTime() % 10 == 0 && this.getWorld().getBlockState(this.pos).getBlock() instanceof BlockSaw)
            if (this.getWorld().getBlockState(this.pos).getValue(BlockSaw.POWERED)) {
                this.activateBlock();
            }
    }
}