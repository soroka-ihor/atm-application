package io.codelions.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidPinCodeValidator implements ConstraintValidator<ValidPinCode, Object> {

    private final  String PIN_CODE_REGEXP = "^[1-9][0-9]{4}$";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void initialize(ValidPinCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object pinCode, ConstraintValidatorContext constraintValidatorContext) {
        return validatePinCode((String) pinCode);
    }

    private boolean validatePinCode(String cardNumber) {
        pattern = Pattern.compile(PIN_CODE_REGEXP);
        matcher = pattern.matcher(cardNumber);
        return matcher.matches();
    }
}
