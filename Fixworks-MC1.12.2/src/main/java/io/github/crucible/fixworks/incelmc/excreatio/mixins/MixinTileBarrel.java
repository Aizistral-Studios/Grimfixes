package io.github.crucible.fixworks.incelmc.excreatio.mixins;

import exnihilocreatio.barrel.BarrelFluidHandler;
import exnihilocreatio.barrel.IBarrelMode;
import exnihilocreatio.config.ModConfig;
import exnihilocreatio.tiles.BaseTileEntity;
import exnihilocreatio.tiles.TileBarrel;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = TileBarrel.class, remap = false)
public abstract class MixinTileBarrel extends BaseTileEntity implements ITickable {

    @Shadow
    private IBarrelMode mode;

    @Shadow
    private BarrelFluidHandler tank;

    private int tickCount = 0;

    /**
     * @author EverNife
     * @reason This light logic can take up to 30% of server tps
     * on overcrowded servers... This alteration will make the tile tick
     * once every two ticks and will not update the light logic anymore.
     *
     * TO DO: Transfer the LightLogic to somewhere else
     */
    @Override
    @Overwrite(remap = true)
    public void update() {
        if (this.getWorld().isRemote)
            return;

        if (this.tickCount++ % 2 == 0)
            return;

        if (ModConfig.mechanics.shouldBarrelsFillWithRain && (this.mode == null || this.mode.getName().equalsIgnoreCase("fluid"))) {
            BlockPos plusY = new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ());
            if (this.getWorld().isRainingAt(plusY)) {
                FluidStack stack = new FluidStack(FluidRegistry.WATER, 4); //double the fill amount as we have reduced the tick count
                this.tank.fill(stack, true);
            }
        }
        if (this.mode != null) {
            this.mode.update((TileBarrel) (Object) this);
        }

        /*[Grimoire] - Remove light logic
        if (getBlockType().getLightValue(getWorld().getBlockState(pos), getWorld(), pos) != getWorld().getLight(pos)) {
            getWorld().checkLight(pos);
            PacketHandler.sendToAllAround(new MessageCheckLight(pos), this);
        }
         */
    }
}