package io.github.crucible.fixworks.chadmc.worldedit;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "WorldEdit")
@ValidatorClass("com.sk89q.worldedit.command.UtilityCommands")
public class WorldEditFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.createBuilder().serverMixins("*").build();
    }

}
