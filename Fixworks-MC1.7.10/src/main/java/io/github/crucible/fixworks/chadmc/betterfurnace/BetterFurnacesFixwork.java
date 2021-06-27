package io.github.crucible.fixworks.chadmc.betterfurnace;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "BetterFurnaces")
@ValidatorClass("at.flabs.betterfurnaces.core.TileEntityBetterFurnace")
public class BetterFurnacesFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
