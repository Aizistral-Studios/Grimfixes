package io.github.crucible.fixworks.core.system;

import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IConfigBuildingEvent;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.ICoreLoadEvent;

public abstract class FixworkController implements IFixValidator {

    public void onConfig(FixworkConfigEvent event) {
        // NO-OP
    }

    @Override
    public boolean validate() {
        return true;
    }

}
