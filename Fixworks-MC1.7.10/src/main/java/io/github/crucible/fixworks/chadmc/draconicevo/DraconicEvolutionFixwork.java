package io.github.crucible.fixworks.chadmc.draconicevo;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "DraconicEvolution")
@ValidatorClass("com.brandon3055.draconicevolution.common.entity.EntityCustomDragon")
@IncompatibleClass("com.gamerforea.draconicevolution.EventConfig")
public class DraconicEvolutionFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
