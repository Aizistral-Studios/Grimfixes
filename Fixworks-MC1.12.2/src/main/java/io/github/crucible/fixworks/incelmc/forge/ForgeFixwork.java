package io.github.crucible.fixworks.incelmc.forge;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.grimoire.common.api.mixin.ConfigurationType;

@Fixwork(id = "Forge", priority = 40000L)
public class ForgeFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.createBuilder().commonMixins("common.*").clientMixins("client.*")
        .configurationType(ConfigurationType.CORE).required(true).build();
    }

}
