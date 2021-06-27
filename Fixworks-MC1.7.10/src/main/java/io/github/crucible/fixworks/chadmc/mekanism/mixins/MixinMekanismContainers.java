package io.github.crucible.fixworks.chadmc.mekanism.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mekanism.common.inventory.container.ContainerAdvancedElectricMachine;
import mekanism.common.inventory.container.ContainerChanceMachine;
import mekanism.common.inventory.container.ContainerChemicalCrystallizer;
import mekanism.common.inventory.container.ContainerChemicalDissolutionChamber;
import mekanism.common.inventory.container.ContainerChemicalInfuser;
import mekanism.common.inventory.container.ContainerChemicalOxidizer;
import mekanism.common.inventory.container.ContainerChemicalWasher;
import mekanism.common.inventory.container.ContainerDictionary;
import mekanism.common.inventory.container.ContainerDigitalMiner;
import mekanism.common.inventory.container.ContainerDynamicTank;
import mekanism.common.inventory.container.ContainerElectricMachine;
import mekanism.common.inventory.container.ContainerElectricPump;
import mekanism.common.inventory.container.ContainerElectrolyticSeparator;
import mekanism.common.inventory.container.ContainerEnergyCube;
import mekanism.common.inventory.container.ContainerFactory;
import mekanism.common.inventory.container.ContainerFilter;
import mekanism.common.inventory.container.ContainerFluidTank;
import mekanism.common.inventory.container.ContainerFluidicPlenisher;
import mekanism.common.inventory.container.ContainerFormulaicAssemblicator;
import mekanism.common.inventory.container.ContainerFuelwoodHeater;
import mekanism.common.inventory.container.ContainerGasTank;
import mekanism.common.inventory.container.ContainerInductionMatrix;
import mekanism.common.inventory.container.ContainerLaserAmplifier;
import mekanism.common.inventory.container.ContainerLaserTractorBeam;
import mekanism.common.inventory.container.ContainerMetallurgicInfuser;
import mekanism.common.inventory.container.ContainerOredictionificator;
import mekanism.common.inventory.container.ContainerPRC;
import mekanism.common.inventory.container.ContainerPersonalChest;
import mekanism.common.inventory.container.ContainerQuantumEntangloporter;
import mekanism.common.inventory.container.ContainerResistiveHeater;
import mekanism.common.inventory.container.ContainerRobitInventory;
import mekanism.common.inventory.container.ContainerRobitMain;
import mekanism.common.inventory.container.ContainerRobitSmelting;
import mekanism.common.inventory.container.ContainerRotaryCondensentrator;
import mekanism.common.inventory.container.ContainerSecurityDesk;
import mekanism.common.inventory.container.ContainerSeismicVibrator;
import mekanism.common.inventory.container.ContainerSolarNeutronActivator;
import mekanism.common.inventory.container.ContainerTeleporter;
import mekanism.common.inventory.container.ContainerThermalEvaporationController;
import mekanism.common.inventory.container.ContainerUpgradeManagement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

@Mixin(value = {
        ContainerAdvancedElectricMachine.class,
        ContainerChanceMachine.class,
        ContainerChemicalCrystallizer.class,
        ContainerChemicalDissolutionChamber.class,
        ContainerChemicalInfuser.class,
        ContainerChemicalOxidizer.class,
        ContainerChemicalWasher.class,
        ContainerDictionary.class,
        ContainerDigitalMiner.class,
        ContainerDynamicTank.class,
        ContainerElectricMachine.class,
        ContainerElectricPump.class,
        ContainerElectrolyticSeparator.class,
        ContainerEnergyCube.class,
        ContainerFactory.class,
        ContainerFilter.class,
        ContainerFluidicPlenisher.class,
        ContainerFluidTank.class,
        ContainerFormulaicAssemblicator.class,
        ContainerFuelwoodHeater.class,
        ContainerGasTank.class,
        ContainerInductionMatrix.class,
        ContainerLaserAmplifier.class,
        ContainerLaserTractorBeam.class,
        ContainerMetallurgicInfuser.class,
        ContainerOredictionificator.class,
        ContainerPersonalChest.class,
        ContainerPRC.class,
        ContainerQuantumEntangloporter.class,
        ContainerResistiveHeater.class,
        ContainerRobitInventory.class,
        ContainerRobitMain.class,
        ContainerRobitSmelting.class,
        ContainerRotaryCondensentrator.class,
        ContainerSecurityDesk.class,
        ContainerSeismicVibrator.class,
        ContainerSolarNeutronActivator.class,
        ContainerTeleporter.class,
        ContainerThermalEvaporationController.class,
        ContainerUpgradeManagement.class
})
public abstract class MixinMekanismContainers {

    /**
     * @author EverNife
     * @reason Disable shift-click thingies to prevent dupes.
     */

    @Inject(method = "transferStackInSlot", at = @At("HEAD"), cancellable = true)
    public void onShiftClick(EntityPlayer player, int slotID, CallbackInfoReturnable<ItemStack> info) {
        info.setReturnValue(null);
    }

}