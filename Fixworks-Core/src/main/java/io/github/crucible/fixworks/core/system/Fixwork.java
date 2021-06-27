package io.github.crucible.fixworks.core.system;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface Fixwork {

    public static final String DEFAULT_ID = "unknown";
    public static final String DEFAULT_DESC = "No description";
    public static final String[] DEFAULT_DEPS = new String[0];
    public static final long DEFAULT_PRIORITY = 0L;
    public static final boolean DEFAULT_ENABLED = false;

    public String id() default DEFAULT_ID;

    public String desc() default DEFAULT_DESC;

    public String[] depends() default {};

    public long priority() default DEFAULT_PRIORITY;

    public boolean defaultEnabled() default DEFAULT_ENABLED;

}
