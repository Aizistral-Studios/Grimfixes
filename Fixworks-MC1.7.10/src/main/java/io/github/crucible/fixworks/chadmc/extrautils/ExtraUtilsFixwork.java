package io.github.crucible.fixworks.chadmc.extrautils;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "ExtraUtilities", depends = "Forge")
@ValidatorClass("com.rwtema.extrautils.item.ItemLawSword")
@IncompatibleClass("com.gamerforea.extrautilities.EventConfig")
public class ExtraUtilsFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
