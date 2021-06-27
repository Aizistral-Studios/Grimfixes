package io.github.crucible.fixworks.chadmc.draconicevo.mixins;

import com.brandon3055.draconicevolution.common.entity.EntityCustomDragon;
import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
import com.brandon3055.draconicevolution.common.utills.LogHelper;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.lang.reflect.Method;

// Faz o dragão disparar o evento de criação do portal.
@Mixin(value = EntityCustomDragon.class, remap = false)
public abstract class MixinEntityCustomDragon extends EntityDragon {

    @Shadow
    public int portalX = 0;

    @Shadow
    public int portalY = 67;

    @Shadow
    public int portalZ = 0;

    @Shadow @Final
    protected boolean isUber;

    @Shadow @Final
    private boolean createPortal;

    public MixinEntityCustomDragon(World world) {
        super(world);
    }

    /**
     * @author juanmuscaria
     * @reason Fazer o Ender Dragon do draconic disparar o mesmo evento do dragão normal.
     */
    @Overwrite
    private void spawnEgg() {
        if (ConfigHandler.dragonEggSpawnLocation[0] != 0 || ConfigHandler.dragonEggSpawnLocation[1] != 0 || ConfigHandler.dragonEggSpawnLocation[1] != 0 && !this.isUber) {
            this.portalX = ConfigHandler.dragonEggSpawnLocation[0];
            this.portalY = ConfigHandler.dragonEggSpawnLocation[1];
            this.portalZ = ConfigHandler.dragonEggSpawnLocation[2];
        }

        BlockEndPortal.field_149948_a = true;

        if (this.createPortal || this.isUber) {
            this.createPortal(this.portalX, this.portalZ);
        }
        LogHelper.info("spawn egg");
        if (this.worldObj.getBlock(this.portalX, this.portalY + 1, this.portalZ) == Blocks.air) {
            this.worldObj.setBlock(this.portalX, this.portalY + 1, this.portalZ, Blocks.dragon_egg);
            LogHelper.info("spawn egg2 " + this.portalX + " " + this.portalY + " " + this.portalZ);
        } else {
            for (int i = this.portalY + 1; i < 250; i++) {
                if (this.worldObj.getBlock(this.portalX, i, this.portalZ) == Blocks.air) {
                    this.worldObj.setBlock(this.portalX, i, this.portalZ, Blocks.dragon_egg);
                    LogHelper.info("spawn egg3");
                    break;
                }
            }
        }

        for (int iX = this.portalX - 2; iX <= this.portalX + 2; iX++) {
            for (int iZ = this.portalZ - 2; iZ <= this.portalZ + 2; iZ++) {

                if (this.worldObj.getBlock(iX, this.portalY - 4, iZ) == Blocks.bedrock && !(iX == this.portalX && iZ == this.portalZ)) {
                    this.worldObj.setBlock(iX, this.portalY - 3, iZ, Blocks.end_portal);
                }
            }
        }

        this.worldObj.setBlock(this.portalX - 1, this.portalY - 1, this.portalZ, Blocks.torch);
        this.worldObj.setBlock(this.portalX + 1, this.portalY - 1, this.portalZ, Blocks.torch);
        this.worldObj.setBlock(this.portalX, this.portalY - 1, this.portalZ - 1, Blocks.torch);
        this.worldObj.setBlock(this.portalX, this.portalY - 1, this.portalZ + 1, Blocks.torch);

        BlockEndPortal.field_149948_a = false;

    }

    private void createPortal(int portalX, int portalZ) {
        try {
            // Does not exist at runtime, thermos method
            Method method = EntityDragon.class.getMethod("func_70975_a", int.class, int.class);
            method.invoke(this, portalX, portalZ);
        } catch (Exception e) {
            // NO-OP
        }
    }
}
