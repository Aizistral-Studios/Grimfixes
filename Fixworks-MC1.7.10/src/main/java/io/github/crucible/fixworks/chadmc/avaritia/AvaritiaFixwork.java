package io.github.crucible.fixworks.chadmc.avaritia;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;
import io.github.crucible.grimoire.common.api.GrimoireAPI;

@Fixwork(id = "Avaritia")
@ValidatorClass("fox.spiteful.avaritia.items.tools.ToolHelper")
@IncompatibleClass("com.gamerforea.avaritia.EventConfig")
public class AvaritiaFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
