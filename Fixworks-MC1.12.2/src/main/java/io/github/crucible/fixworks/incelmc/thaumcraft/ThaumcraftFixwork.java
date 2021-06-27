package io.github.crucible.fixworks.incelmc.thaumcraft;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "Thaumcraft")
@ValidatorClass("thaumcraft.common.blocks.essentia.BlockSmelter")
public class ThaumcraftFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
