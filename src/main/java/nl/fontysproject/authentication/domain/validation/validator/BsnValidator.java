package nl.fontysproject.authentication.domain.validation.validator;

import nl.fontysproject.authentication.domain.validation.annotation.Bsn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class BsnValidator implements ConstraintValidator<Bsn, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        // Test if value consist of numerical characters only.
        // Ex: 123456789 -> true
        //     1234ABC89 -> false
        if (!value.matches("^\\d+$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A BSN can only contain alphanumerical digits.");
            return false;
        }

        // BSN numbers must always be 9 digits long.
        // Otherwise the number is not valid.
        if (value.length() != 9) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A BSN must consist of exactly 9 alphanumerical digits.");
            return false;
        }

        int[] numbers = toNumbers(value);

        // Test whether the given BSN is valid by the 11-check digit.
        // https://en.wikipedia.org/wiki/Check_digit
        return ((numbers[0] * 9) +
                (numbers[1] * 8) +
                (numbers[2] * 7) +
                (numbers[3] * 6) +
                (numbers[4] * 5) +
                (numbers[5] * 4) +
                (numbers[6] * 3) +
                (numbers[7] * 2) +
                (numbers[8] * -1)) % 11 == 0;
    }

    private int[] toNumbers(String value) {
        return Arrays
                .stream(value.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}

