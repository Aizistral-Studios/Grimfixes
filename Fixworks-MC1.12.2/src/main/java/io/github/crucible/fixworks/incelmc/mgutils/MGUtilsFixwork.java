package io.github.crucible.fixworks.incelmc.mgutils;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "MobGrindUtils")
@ValidatorClass("mob_grinding_utils.tile.TileEntitySaw")
public class MGUtilsFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
