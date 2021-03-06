package io.github.crucible.fixworks.incelmc.worldedit;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "ForgeWorldEdit")
@ValidatorClass("com.sk89q.worldedit.extension.factory.BlockFactory")
public class WorldEditFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
