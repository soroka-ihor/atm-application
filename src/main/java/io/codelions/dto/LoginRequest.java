package io.codelions.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String cardNumber;
    private String pinCode;
}
