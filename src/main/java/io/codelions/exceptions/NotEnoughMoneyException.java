package io.codelions.exceptions;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("Not enough money.");
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
