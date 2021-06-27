package io.github.crucible.fixworks.chadmc.cpblocks;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "CarpentersBlocks")
@ValidatorClass("com.carpentersblocks.util.handler.PacketHandler")
@IncompatibleClass("com.gamerforea.carpentersblocks.EventConfig")
public class CarpentersFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
