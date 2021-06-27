package io.github.crucible.fixworks.chadmc.tcenergistics;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "ThaumicEnergistics")
@ValidatorClass("thaumicenergistics.common.container.ContainerPartArcaneCraftingTerminal")
@IncompatibleClass("com.gamerforea.thaumicenergistics.EventConfig")
public class ThaumicEnergisticsFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
