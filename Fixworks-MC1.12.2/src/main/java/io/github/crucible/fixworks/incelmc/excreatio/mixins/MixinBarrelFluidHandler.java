package io.github.crucible.fixworks.incelmc.excreatio.mixins;

import exnihilocreatio.barrel.BarrelFluidHandler;
import exnihilocreatio.barrel.IBarrelMode;
import exnihilocreatio.networking.MessageBarrelModeUpdate;
import exnihilocreatio.networking.MessageFluidUpdate;
import exnihilocreatio.networking.PacketHandler;
import exnihilocreatio.registries.manager.ExNihiloRegistryManager;
import exnihilocreatio.registries.registries.BarrelModeRegistry;
import exnihilocreatio.tiles.TileBarrel;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.concurrent.*;

@Mixin(value = BarrelFluidHandler.class, remap = false)
public abstract class MixinBarrelFluidHandler extends FluidTank {

    @Shadow
    private TileBarrel barrel;

    private static ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public MixinBarrelFluidHandler(int capacity) {
        super(capacity);
    }

    /**
     * @author EverNife
     * @reason Do some part of Fill logic assync,
     * mainly the packthandler process can all be done assync
     */
    @Override
    @Overwrite
    public int fill(FluidStack resource, boolean doFill) {
        if (this.barrel.getMode() != null && !this.barrel.getMode().canFillWithFluid(this.barrel))
            return 0;

        int amount = super.fill(resource, doFill);
        if (amount > 0) {
            executorService.submit(() -> { //[GRIMOIRE] This can go assync!
                PacketHandler.sendToAllAround(new MessageFluidUpdate(this.fluid, this.barrel.getPos()), this.barrel);
                if (this.fluid != null && this.barrel.getMode() == null) {
                    this.barrel.setMode("fluid");
                    PacketHandler.sendToAllAround(new MessageBarrelModeUpdate(this.barrel.getMode().getName(), this.barrel.getPos()), this.barrel);
                }
            });
        }
        return amount;
    }

    /**
     * @author EverNife
     * @reason Nobody knows
     */

    @Override
    @Overwrite
    public boolean canFillFluidType(FluidStack fluid) {
        if (fluid == null || fluid.getFluid() == null || ExNihiloRegistryManager.BARREL_LIQUID_BLACKLIST_REGISTRY.isBlacklisted(this.barrel.getTier(), fluid.getFluid().getName()))
            return false;

        for (IBarrelMode mode : BarrelModeRegistry.getModes(BarrelModeRegistry.TriggerType.FLUID)) {
            if (mode.isTriggerFluidStack(fluid))
                return true;
        }
        return false;
    }
}