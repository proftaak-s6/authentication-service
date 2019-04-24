package nl.fontysproject.authentication.domain.validation.annotation;

import nl.fontysproject.authentication.domain.validation.validator.AllowedValuesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {AllowedValuesValidator.class})
public @interface AllowedValues {

    String message() default "Invalid value present in list.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values() default {};

    boolean allowEmpty() default false;
}