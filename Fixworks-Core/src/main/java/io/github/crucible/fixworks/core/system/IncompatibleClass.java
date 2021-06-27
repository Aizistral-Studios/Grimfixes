package io.github.crucible.fixworks.core.system;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * For 1.7.10 modules, it often happens that whatever things they patch
 * are already rectified by much vaster and all-encompassing gamerforEA's
 * fixes. This annotation is mostly used to prevent modules from loading
 * if whatever mod they target is a gamerforEA-patched build.<br/><br/>
 *
 * If class name specified in this annotation's {@link #value()} does exist
 * in runtime at the moment module's mixin configuration is about to be
 * applied, that configuration will be prevented from loading.
 *
 * @author Aizistral
 */

@Retention(RUNTIME)
@Target(TYPE)
public @interface IncompatibleClass {

    public String value();

}