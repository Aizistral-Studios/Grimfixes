package io.github.crucible.fixworks.core;

import io.github.crucible.fixworks.core.system.FixworkContainer;
import io.github.crucible.grimoire.common.api.GrimoireConstants;
import io.github.crucible.grimoire.common.api.eventbus.CoreEventHandler;
import io.github.crucible.grimoire.common.api.eventbus.SubscribeCoreEvent;
import io.github.crucible.grimoire.common.api.events.configurations.MixinConfigLoadEvent;
import io.github.crucible.grimoire.common.api.mixin.IMixinConfiguration;

@CoreEventHandler(value = GrimoireConstants.MAIN_BUS_NAME, mandatory = true)
public class FixworksHandler {

    @SubscribeCoreEvent
    public static void onConfigurationLoad(MixinConfigLoadEvent event) {
        event.getOwner().ifPresent(owner -> {
            if (owner == FixworksCore.getInstance()) {
                IMixinConfiguration config = event.getConfiguration();
                FixworkContainer container = FixworksCore.getInstance().seekOwner(config);

                if (container != null && !container.validate()) {
                    FixworksCore.logger.info("Canceling load of configuration {}, since its conditions are not satisfied.", config.getClasspath());
                    event.setCanceled(true);
                }
            }
        });
    }

}
