package nl.fontysproject.authentication.domain.validation.validator;

import nl.fontysproject.authentication.domain.validation.annotation.AllowedValues;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AllowedValuesValidator implements ConstraintValidator<AllowedValues, Set<String>> {

    private List<String> allowedValues;
    private boolean allowEmpty;

    @Override
    public void initialize(AllowedValues constraintAnnotation) {
        allowedValues = Arrays.asList(constraintAnnotation.values());
        allowEmpty = constraintAnnotation.allowEmpty();
    }

    @Override
    public boolean isValid(Set<String> values, ConstraintValidatorContext context) {
        if (!allowEmpty && values.size() == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Provide one or more values.").addConstraintViolation();
            return false;
        }

        List<String> invalidValues = new ArrayList<>();

        for (String value : values) {
            if (!allowedValues.contains(value)) {
                invalidValues.add(value);
            }
        }

        if (!invalidValues.isEmpty()) {
            String message = "[" + String.join(", ", invalidValues) + "] are not allowed, please choose from [" + String.join(", ", allowedValues) + "]";
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

            return false;
        }

        return true;
    }
}
