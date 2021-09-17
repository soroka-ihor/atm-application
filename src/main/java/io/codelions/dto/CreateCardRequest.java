package io.codelions.dto;

import io.codelions.validations.ValidCardNumber;
import io.codelions.validations.ValidPinCode;
import lombok.Data;

@Data
public class CreateCardRequest {

    @ValidCardNumber
    private String cardNumber;
    @ValidPinCode
    private String pinCode;

    private String cvv2;
    private Long validity;
    private String username;
}
