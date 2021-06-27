package io.github.crucible.fixworks.chadmc.thermaldynamics;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "ThermalDynamics")
@ValidatorClass("cofh.thermaldynamics.duct.item.TileItemDuct")
@IncompatibleClass("com.gamerforea.cofh.EventConfig")
public class ThermalDynamicsFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
