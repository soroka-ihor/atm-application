package io.codelions.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferMoneyRequest {
    private String from;
    private String to;
    private Double amountOfMoney;
}
