package io.github.crucible.fixworks.chadmc.botania;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.IncompatibleClass;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "Botania", depends = "Forge")
@ValidatorClass("vazkii.botania.common.entity.EntityEnderAirBottle")
@IncompatibleClass("com.gamerforea.botania.EventConfig")
public class BotaniaFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
