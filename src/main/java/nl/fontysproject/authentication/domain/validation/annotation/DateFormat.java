package nl.fontysproject.authentication.domain.validation.annotation;

import nl.fontysproject.authentication.domain.validation.validator.DateFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DateFormatValidator.class)
@Documented
public @interface DateFormat {

    String message() default "Invalid date format, please use dd/MM/yyyy";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String pattern();

    boolean onlyPast() default true;
}
