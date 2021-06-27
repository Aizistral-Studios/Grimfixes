package io.github.crucible.fixworks.chadmc.forge.mixins.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.crucible.fixworks.chadmc.forge.implementation.FakePlayerManager;
import io.github.crucible.fixworks.chadmc.forge.implementation.ITileEntity;
import io.github.crucible.fixworks.chadmc.forge.implementation.UserIdent;

@Mixin(TileEntity.class)
public abstract class MixinTileEntity implements ITileEntity {

    private UserIdent tileOwner = UserIdent.nobody;

    @Shadow
    public abstract World getWorldObj();

    @Override
    public UserIdent getUserIdent() {
        return this.tileOwner;
    }

    @Override
    public void setUserIdent(UserIdent ident) {
        this.tileOwner = ident;
    }

    @Override
    public EntityPlayer getFakePlayer() {
        if (this.tileOwner.equals(UserIdent.nobody))
            return FakePlayerManager.get((WorldServer) this.getWorldObj());
        else
            return FakePlayerManager.get((WorldServer) this.getWorldObj(), this.tileOwner);
    }

    @Inject(method = "readFromNBT", at = @At("HEAD"))
    private void readInject(NBTTagCompound nbtTagCompound, CallbackInfo callback) {
        if (UserIdent.existsInNbt(nbtTagCompound, "crucibledata.owner")) {
            this.tileOwner = UserIdent.readfromNbt(nbtTagCompound, "crucibledata.owner");
        } else {
            this.tileOwner = UserIdent.nobody;
        }

    }

    @Inject(method = "writeToNBT", at = @At("HEAD"))
    private void writeInject(NBTTagCompound nbtTagCompound, CallbackInfo callback) {
        this.tileOwner.saveToNbt(nbtTagCompound, "crucibledata.owner");
    }
}
