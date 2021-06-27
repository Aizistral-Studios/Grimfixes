package io.github.crucible.fixworks.core.system;

import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IConfigBuildingEvent;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.ICoreLoadEvent;

/**
 * Controller class for receiving module lifecycle events.<br/>
 * In general it is assumed that all modules will use runtime-generated mixin
 * configurations, creation of which is possible by overriding
 * {@link #onConfig(FixworkConfigEvent)}.
 *
 * @author Aizistral
 */

public abstract class FixworkController implements IFixValidator {

    public void onConfig(FixworkConfigEvent event) {
        // NO-OP
    }

    @Override
    public boolean validate() {
        return true;
    }

}
