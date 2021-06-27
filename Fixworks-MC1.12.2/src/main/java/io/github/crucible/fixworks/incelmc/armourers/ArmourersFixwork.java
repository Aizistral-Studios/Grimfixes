package io.github.crucible.fixworks.incelmc.armourers;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "ArmourersWorkshop")
@ValidatorClass("moe.plushie.armourers_workshop.common.init.blocks.BlockMannequin")
public class ArmourersFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}