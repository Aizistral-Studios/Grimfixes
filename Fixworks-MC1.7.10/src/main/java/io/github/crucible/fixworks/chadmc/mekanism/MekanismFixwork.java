package io.github.crucible.fixworks.chadmc.mekanism;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "Mekanism", depends = "Forge")
@ValidatorClass("mekanism.common.entity.EntityFlame")
@IncompatibleClass("com.gamerforea.mekanism.EventConfig")
public class MekanismFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
