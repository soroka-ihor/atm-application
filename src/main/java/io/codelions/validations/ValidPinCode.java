package io.codelions.validations;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidPinCodeValidator.class)
@Documented
public @interface ValidPinCode {
    String message() default "Invalid pin code. Pin code must contains only 4 digits.";
}
