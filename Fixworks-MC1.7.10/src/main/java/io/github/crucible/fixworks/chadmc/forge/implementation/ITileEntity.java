package io.github.crucible.fixworks.chadmc.forge.implementation;

import net.minecraft.entity.player.EntityPlayer;

public interface ITileEntity {

    public UserIdent getUserIdent();

    public void setUserIdent(UserIdent ident);

    public EntityPlayer getFakePlayer();

}
