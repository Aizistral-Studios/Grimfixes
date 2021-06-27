package io.github.crucible.fixworks.chadmc.cofh;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "CoFHCore")
@ValidatorClass("cofh.thermalexpansion.block.cache.TileCache")
@IncompatibleClass("com.gamerforea.cofh.EventConfig")
public class CoFHFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
