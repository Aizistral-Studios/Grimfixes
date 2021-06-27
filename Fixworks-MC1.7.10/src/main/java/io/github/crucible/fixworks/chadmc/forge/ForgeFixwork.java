package io.github.crucible.fixworks.chadmc.forge;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.grimoire.common.api.mixin.ConfigurationType;

@Fixwork(id = "Forge", priority = 40000L)
public class ForgeFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.createBuilder()
        .configurationType(ConfigurationType.CORE)
        .commonMixins("common.*")
        .clientMixins("client.*")
        .required(true)
        .build();
    }

}
