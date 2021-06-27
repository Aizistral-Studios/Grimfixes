package io.github.crucible.fixworks.core.system;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marks class as its own module. Classes annotated with this must reside in
 * either 'io.github.crucible.fixworks.chadmc' or 'io.github.crucible.fixworks.incelmc',
 * depending on MC version they target, and extend {@link FixworkController}. They will
 * automatically be discovered and loaded.
 *
 * @see {@link ValidatorClass}, {@link IncompatibleClass}
 * @author Aizistral
 */

@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface Fixwork {

    public static final String DEFAULT_ID = "unknown";
    public static final String DEFAULT_DESC = "No description.";
    public static final String[] DEFAULT_DEPS = new String[0];
    public static final long DEFAULT_PRIORITY = 0L;
    public static final boolean DEFAULT_ENABLED = true;

    /**
     * @return String ID of this module, prefferably something human-realadable.
     * Normally this would be the name of the mod particular module targets.
     */
    public String id() default DEFAULT_ID;

    /**
     * @return Human-readable description of this module. Will be used when generating
     * config files for it.
     */
    public String desc() default DEFAULT_DESC;

    /**
     * @return An array of module names this module depends on. If used has a module enabled
     * but some of its mandatory dependencies disabled in config, this will raise an exception.
     */

    public String[] depends() default {};

    /**
     * @return Priority, for sorting module registry.
     */

    public long priority() default DEFAULT_PRIORITY;

    /**
     * @return Whether this module is enabled or not in config file by default.
     */

    public boolean defaultEnabled() default DEFAULT_ENABLED;

}
