package io.github.crucible.fixworks.incelmc.extrabotany;

import io.github.crucible.fixworks.core.system.Fixwork;
import io.github.crucible.fixworks.core.system.FixworkConfigEvent;
import io.github.crucible.fixworks.core.system.FixworkController;
import io.github.crucible.fixworks.core.system.ValidatorClass;

@Fixwork(id = "ExtraBotany")
@ValidatorClass("com.meteor.extrabotany.common.entity.EntitySubspace")
public class ExtraBotanyFixwork extends FixworkController {

    @Override
    public void onConfig(FixworkConfigEvent event) {
        event.defaultConfiguration();
    }

}
