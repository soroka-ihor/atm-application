package io.codelions.exceptions;

public class NoMoneyInAtmException extends RuntimeException {
    public NoMoneyInAtmException(String atm_does_not_have_money) {
        super(atm_does_not_have_money);
    }
}
