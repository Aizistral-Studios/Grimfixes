package io.github.crucible.fixworks.chadmc.nei;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "NotEnoughItems")
@ValidatorClass("codechicken.nei.NEIServerUtils")
public class NEIFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
