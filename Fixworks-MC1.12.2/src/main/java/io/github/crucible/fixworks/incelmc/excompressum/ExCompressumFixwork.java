package io.github.crucible.fixworks.incelmc.excompressum;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "ExCompressum")
@ValidatorClass("net.blay09.mods.excompressum.block.BlockAutoSieveBase")
public class ExCompressumFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}