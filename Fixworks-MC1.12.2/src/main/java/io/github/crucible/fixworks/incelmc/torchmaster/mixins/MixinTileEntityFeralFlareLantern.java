package io.github.crucible.fixworks.incelmc.torchmaster.mixins;

import net.xalcon.torchmaster.common.tiles.TileEntityFeralFlareLantern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = TileEntityFeralFlareLantern.class, remap = false)
public abstract class MixinTileEntityFeralFlareLantern {

    /**
     * @author EverNife
     * @reason
     *
     * Disable FeralFlare Lantern because it works as a ChunkLoader in some
     * cases
     */
    @Overwrite(remap = true)
    public void update() {
        // Entirely disable FeralFlareLanter

        // Maybe someone in the future wants to fix it, or even open a issue for the mod itself, as it is still being developed.
    }
}