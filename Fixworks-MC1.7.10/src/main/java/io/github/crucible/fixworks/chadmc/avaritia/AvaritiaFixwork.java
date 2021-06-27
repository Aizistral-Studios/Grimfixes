package io.github.crucible.fixworks.chadmc.avaritia;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.grimoire.common.api.GrimoireAPI;

@Fixwork(id = "Avaritia")
public class AvaritiaFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
