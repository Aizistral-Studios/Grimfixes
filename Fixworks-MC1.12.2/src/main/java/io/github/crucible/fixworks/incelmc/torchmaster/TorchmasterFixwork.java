package io.github.crucible.fixworks.incelmc.torchmaster;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "Torchmaster")
@ValidatorClass("net.xalcon.torchmaster.common.tiles.TileEntityFeralFlareLantern")
public class TorchmasterFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
