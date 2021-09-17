package io.codelions.dto;

import lombok.Data;

@Data
public class WithdrawCashRequest {
    private String cardNumber;
    private Integer amount;
}
