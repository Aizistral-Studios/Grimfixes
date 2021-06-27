package io.github.crucible.fixworks.chadmc.cpblocks.mixins;

import com.carpentersblocks.network.PacketEnrichPlant;
import com.carpentersblocks.network.TilePacket;
import com.carpentersblocks.tileentity.TECarpentersFlowerPot;
import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;

@Mixin(value = PacketEnrichPlant.class, remap = false)
public class MixinPacketEnrichPlant extends TilePacket {
    @Shadow
    private int hexColor;

    /**
     * @author juanmuscaria
     * @reason Possível fix para um hack.
     */
    @Override
    @Overwrite
    public void processData(EntityPlayer entityPlayer, ByteBufInputStream bbis) throws IOException {
        super.processData(entityPlayer, bbis);
        World world = entityPlayer.worldObj;
        this.hexColor = bbis.readInt();
        if (!EHIntegration.canInteract(entityPlayer, entityPlayer.getCurrentEquippedItem(), this.x, this.y, this.z, ForgeDirection.UNKNOWN))
            // TODO: 29/10/2019 Dar kick no player.
            return;
        TECarpentersFlowerPot TE = (TECarpentersFlowerPot) world.getTileEntity(this.x, this.y, this.z);

        if (TE != null) {
            if (this.hexColor != 16777215 && !TE.hasAttribute((byte) 24)) {
                TE.addAttribute((byte) 24, new ItemStack(Items.dye, 1, 15));
                if (!entityPlayer.capabilities.isCreativeMode && --entityPlayer.getCurrentEquippedItem().stackSize <= 0) {
                    entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                }
            }
        }
    }
}
