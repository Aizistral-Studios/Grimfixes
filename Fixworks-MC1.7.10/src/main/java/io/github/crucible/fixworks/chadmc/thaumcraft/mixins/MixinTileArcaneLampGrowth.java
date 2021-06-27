package io.github.crucible.fixworks.chadmc.thaumcraft.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import thaumcraft.common.tiles.TileArcaneLampGrowth;

@Pseudo
@Mixin(value = TileArcaneLampGrowth.class, remap = false)
public class MixinTileArcaneLampGrowth {

    /**
     * @author EverNife
     * @reason Desativa esse item imundo...
     *
     * 49 lampadas dessa fazem o trabalho de 1750 sprinklers
     *
     * https://media.discordapp.net/attachments/211161697421885441/671403055568388096/Growth_Lamp.gif?
     */
    @Overwrite(remap = true)
    public void updateEntity() {

    }

    /**
     * @author EverNife
     * @reason Desativa esse item imundo...
     *
     * 49 lampadas dessa fazem o trabalho de 1750 sprinklers
     *
     * https://media.discordapp.net/attachments/211161697421885441/671403055568388096/Growth_Lamp.gif?
     */
    @Overwrite()
    private void updatePlant() {

    }

    /**
     * @author EverNife
     * @reason Desativa esse item imundo...
     *
     * 49 lampadas dessa fazem o trabalho de 1750 sprinklers
     *
     * https://media.discordapp.net/attachments/211161697421885441/671403055568388096/Growth_Lamp.gif?
     */
    @Overwrite()
    boolean drawEssentia() {
        return false;
    }

    /**
     * @author EverNife
     * @reason Desativa esse item imundo...
     *
     * 49 lampadas dessa fazem o trabalho de 1750 sprinklers
     *
     * https://media.discordapp.net/attachments/211161697421885441/671403055568388096/Growth_Lamp.gif?
     */
    @Overwrite()
    public boolean canUpdate() {
        return false;
    }

}
