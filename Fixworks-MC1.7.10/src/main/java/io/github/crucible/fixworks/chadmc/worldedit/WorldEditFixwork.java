package io.github.crucible.fixworks.chadmc.worldedit;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;

@Fixwork(id = "WorldEdit")
public class WorldEditFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.createBuilder().serverMixins("*").build();
    }

}
