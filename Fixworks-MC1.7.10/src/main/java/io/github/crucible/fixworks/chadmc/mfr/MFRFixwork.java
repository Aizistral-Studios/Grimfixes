package io.github.crucible.fixworks.chadmc.mfr;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "MineFactoryReloaded")
@ValidatorClass("powercrystals.minefactoryreloaded.block.BlockRubberSapling")
@IncompatibleClass("com.gamerforea.minefactoryreloaded.EventConfig")
public class MFRFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
