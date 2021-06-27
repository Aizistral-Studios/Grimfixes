package io.github.crucible.fixworks.chadmc.thaumcraft;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "Thaumcraft", depends = "Forge")
@ValidatorClass("thaumcraft.common.blocks.BlockTable")
@IncompatibleClass("com.gamerforea.thaumcraft.EventConfig")
public class ThaumcraftFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}