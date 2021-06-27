package io.github.crucible.fixworks.chadmc.nei.mixins;

import codechicken.lib.packet.PacketCustom;
import codechicken.nei.*;
import io.github.crucible.grimoire.mc1_7_10.api.integration.eventhelper.EHIntegration;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Created by EverNife on 22/08/2019.
 */

@Mixin(value = NEIServerUtils.class, remap = false)
public abstract class MixinNEIServerUtils {

    @Shadow
    public static WorldSettings.GameType getGameType(int mode) {
        return null;
    }

    /**
     * @author EverNife
     * @reason Previnir hackers que acabam burlando o sistema de packets!
     *
     * Adicionado a permissão "nei.gamemode" para validar o processo!
     */
    @Overwrite
    public static void setGamemode(EntityPlayerMP player, int mode) {
        if (mode < 0 || mode >= NEIActions.gameModes.length || NEIActions.nameActionMap.containsKey(NEIActions.gameModes[mode]) && !NEIServerConfig.canPlayerPerformAction(player.getCommandSenderName(), NEIActions.gameModes[mode]))
            return;

        // creative+
        NEIServerConfig.forPlayer(player.getCommandSenderName()).enableAction("creative+", mode == 2);
        if (mode == 2 && !(player.openContainer instanceof ContainerCreativeInv)) {
            NEISPH.processCreativeInv(player, true);
        }

        // change it on the server
        WorldSettings.GameType newGamemode = getGameType(mode);
        NEIServerConfig.logger.info("[NEI] Changing player " + player.getCommandSenderName() + " gamemode to [" + newGamemode + "]");
        MinecraftServer.getServer().getCommandManager().executeCommand(player, "gamemode " + newGamemode);

        if (EHIntegration.hasPermission(player, "nei.gamemode")) {
            player.theItemInWorldManager.setGameType(newGamemode);

            // tell the client to change it
            (new PacketCustom("NEI", 14)).writeByte(mode).sendToPlayer(player);
            player.addChatMessage(new ChatComponentText("§aChanging gamemode to [" + newGamemode.getName() + "]"));
        }
    }

}