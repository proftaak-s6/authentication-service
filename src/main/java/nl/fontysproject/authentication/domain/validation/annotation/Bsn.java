package nl.fontysproject.authentication.domain.validation.annotation;

import nl.fontysproject.authentication.domain.validation.validator.BsnValidator;

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
@Constraint(validatedBy = { BsnValidator.class })
public @interface Bsn {

    String message() default "Must be a valid BSN";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}