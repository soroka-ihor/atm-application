package io.codelions.exceptions;

public class PinCodeIncorrectException extends RuntimeException {
    public PinCodeIncorrectException(String message) {
        super(message);
    }
}
