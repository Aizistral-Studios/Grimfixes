package io.github.crucible.fixworks.core.system;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;

import io.github.crucible.fixworks.core.FixworksCore;
import io.github.crucible.grimoire.common.api.GrimoireAPI;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IConfigBuildingEvent;
import io.github.crucible.grimoire.common.api.mixin.IMixinConfiguration;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class FixworkContainer implements IFixValidator, Comparable<FixworkContainer> {
    private final String className, id, pkg, validatorClass, incompatibleClass, desc;
    private final List<IMixinConfiguration> ownedConfigurations = new ArrayList<>();
    private final long priority;
    private final boolean defaultEnabled;
    private final ImmutableList<String> dependendencies;

    private FixworkController controller = null;
    private Boolean valid = null;

    public FixworkContainer(FixworkVisitor visitor, String pkg) {
        this.className = visitor.getClassName();
        this.id = visitor.getFixworkID();
        this.priority = visitor.getPriority();
        this.validatorClass = visitor.getValidatorClass();
        this.incompatibleClass = visitor.getIncompatibleClass();
        this.desc = visitor.getFixworkDesc();
        this.defaultEnabled = visitor.isDefaultEnabled();
        this.dependendencies = new ImmutableList.Builder<String>().addAll(visitor.getDependencies()).build();

        this.pkg = pkg;
    }

    @Override
    public boolean validate() {
        if (this.valid != null)
            return this.valid;

        if (this.className.equals(this.validatorClass)) {
            this.valid = this.controller.validate();
            return this.valid;
        }

        LaunchClassLoader loader = GrimoireAPI.getLaunchClassloader();

        try {
            byte[] bs;

            if (this.validatorClass != null) {
                bs = loader.getClassBytes(this.validatorClass);

                if (bs != null) {
                    this.valid = true;
                } else {
                    this.valid = false;
                    return this.valid;
                }
            } else {
                this.valid = true;
            }

            if (this.incompatibleClass != null) {
                bs = loader.getClassBytes(this.incompatibleClass);

                if (bs != null) {
                    this.valid = false;
                }
            }
        } catch (Exception ex) {
            this.valid = false;
        }

        return this.valid;
    }

    @SuppressWarnings("unchecked")
    public void construct() {
        if (this.controller != null)
            return;

        try {
            Class<? extends FixworkController> workClass = (Class<? extends FixworkController>) Class.forName(this.className);
            Constructor<? extends FixworkController> constructor = workClass.getConstructor();
            constructor.setAccessible(true);
            this.controller = constructor.newInstance();
        } catch (Exception ex) {
            Throwables.propagate(ex);
        }
    }

    public void sendConfig(IConfigBuildingEvent event) {
        FixworkConfigEvent fixEvent = new FixworkConfigEvent(event, this.id, this.pkg);
        this.controller.onConfig(fixEvent);
    }

    public void ownConfig(IMixinConfiguration config) {
        this.ownedConfigurations.add(config);
    }

    public boolean isOwner(IMixinConfiguration config) {
        return this.ownedConfigurations.contains(config);
    }

    public String getDescription() {
        return this.desc;
    }

    public String getID() {
        return this.id;
    }

    public long getPriority() {
        return this.priority;
    }

    public boolean isValid() {
        return this.valid != null && this.valid.booleanValue();
    }

    public boolean isDefaultEnabled() {
        return this.defaultEnabled;
    }

    public boolean hasDependencies() {
        return this.dependendencies != null && this.dependendencies.size() > 0;
    }

    public String depsDesc() {
        return "Dependencies: " + this.dependendencies.stream().collect(Collectors.joining(", "));
    }

    public void verifyDependencies() {
        if (this.hasDependencies()) {
            for (String dep : this.dependendencies) {
                boolean exists = false;
                for (FixworkContainer container : FixworksCore.getInstance().getFixworks()) {
                    if (container.getID().equals(dep)) {
                        exists = true;
                        break;
                    }
                }

                if (!exists)
                    throw new IllegalStateException("Fixworks module " + this.getID() + " is enabled, but its mandatory dependency "
                            + dep + " is not loaded!");
            }
        }
    }

    @Override
    public int compareTo(FixworkContainer other) {
        return Long.compare(this.priority, other.priority);
    }

}
