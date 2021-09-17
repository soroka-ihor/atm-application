package io.codelions.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidCardNumberValidator implements ConstraintValidator<ValidCardNumber, Object> {

    private final String VISA_MASTER_CARD_REGEXP = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public boolean isValid(Object cardNumber, ConstraintValidatorContext constraintValidatorContext) {
        return validateCardNumber((String)cardNumber);
    }

    @Override
    public void initialize(ValidCardNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private boolean validateCardNumber(String cardNumber) {
        pattern = Pattern.compile(VISA_MASTER_CARD_REGEXP);
        matcher = pattern.matcher(cardNumber);
        return matcher.matches();
    }
}
