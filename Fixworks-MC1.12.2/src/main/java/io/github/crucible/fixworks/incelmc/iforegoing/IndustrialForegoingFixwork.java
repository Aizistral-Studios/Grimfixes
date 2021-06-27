package io.github.crucible.fixworks.incelmc.iforegoing;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "IndustrialForegoing")
@ValidatorClass("com.buuz135.industrial.tile.misc.BlackHoleUnitTile")
public class IndustrialForegoingFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
