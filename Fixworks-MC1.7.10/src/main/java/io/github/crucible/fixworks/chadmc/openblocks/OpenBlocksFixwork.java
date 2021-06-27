package io.github.crucible.fixworks.chadmc.openblocks;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "OpenBlocks")
@ValidatorClass("openblocks.common.item.ItemCursor")
@IncompatibleClass("com.gamerforea.openblocks.EventConfig")
public class OpenBlocksFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
