package io.github.crucible.fixworks.core.system;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * In most instances it is desirable to prevent module mixin configuration from
 * being loaded if classes it targets do not exist in runtime (for instance, user
 * doesn't have respective mod installed). This annotation can be used to specify
 * arbitrary class from targeted jar, and if such class does not exist at the time
 * module configuration is attempted to be loaded - loading will be prevented.<br/>
 * <br/>
 * Validation process <b>does not</b> load target class.</br></br>
 *
 * Special use-case would be specifying module's own class as validator; if this
 * happens, {@link FixworkController#validate()} method will be used for ensuring
 * module validity, which might be useful for non-conventional verification procedures.
 *
 * @author Aizistral
 */

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ValidatorClass {

    public String value();

}
