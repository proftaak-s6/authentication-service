package nl.fontysproject.authentication.domain.validation.validator;

import nl.fontysproject.authentication.domain.validation.annotation.DateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {

    private DateTimeFormatter formatter;
    private boolean onlyPast;
    private String pattern;

    @Override
    public void initialize(DateFormat constraint) {
        pattern = constraint.pattern();
        onlyPast = constraint.onlyPast();
        formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }

        try {
            LocalDate date = LocalDate.parse(value, formatter);

            if (onlyPast && date.isAfter(LocalDate.now()))
            {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Date must be in the past.").addConstraintViolation();
                return false;
            }

            return true;

        } catch (Exception e) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Given date does not fit required pattern '" + pattern + "'").addConstraintViolation();
            return false;
        }
    }
}

