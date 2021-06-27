package io.github.crucible.fixworks.core.system;

import io.github.crucible.fixworks.core.FixworksCore;
import io.github.crucible.grimoire.common.api.GrimoireAPI;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IConfigBuildingEvent;
import io.github.crucible.grimoire.common.api.mixin.ConfigurationType;
import io.github.crucible.grimoire.common.api.mixin.IMixinConfiguration;
import io.github.crucible.grimoire.common.api.mixin.IMixinConfigurationBuilder;

/**
 * Wraps around {@link IConfigBuildingEvent}, providing module-specific helping methods
 * when building runtime mixin configurations.
 *
 * @author Aizistral
 */

public class FixworkConfigEvent {
    private final IConfigBuildingEvent event;
    private final String fixworkID;
    private final String pkg;

    public FixworkConfigEvent(IConfigBuildingEvent event, String fixworkID, String pkg) {
        this.event = event;
        this.pkg = pkg;
        this.fixworkID = fixworkID;
    }

    public IMixinConfiguration defaultConfiguration() {
        return this.createBuilder().commonMixins("*").build();
    }

    public IMixinConfigurationBuilder createBuilder() {
        return this.createBuilder("mixins." + this.fixworkID.toLowerCase() + ".json");
    }

    public IMixinConfigurationBuilder createBuilder(String configurationPath) {
        IMixinConfigurationBuilder builder =
                this.event.createBuilder("fixworks/" + configurationPath)
                .mixinPackage(this.getMixinPackage())
                .verbose(true)
                .required(!GrimoireAPI.isDevEnvironment())
                .configurationType(ConfigurationType.MOD);

        if (!GrimoireAPI.isDevEnvironment()) {
            builder.refmap(FixworksCore.REFMAP_NAME);
        }

        return builder;
    }

    public String getFixworkPackage() {
        return this.pkg;
    }

    public String getMixinPackage() {
        return this.pkg + ".mixins";
    }

}
