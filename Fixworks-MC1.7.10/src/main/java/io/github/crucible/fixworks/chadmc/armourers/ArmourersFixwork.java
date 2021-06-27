package io.github.crucible.fixworks.chadmc.armourers;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IConfigBuildingEvent;

@Fixwork(id = "ArmourersWorkshop")
@ValidatorClass("riskyken.armourersWorkshop.common.blocks.BlockMannequin")
@IncompatibleClass("com.gamerforea.aw.ModUtils")
public class ArmourersFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
