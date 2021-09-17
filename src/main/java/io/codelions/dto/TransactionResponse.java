package io.codelions.dto;

import io.codelions.enums.TransactionType;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class TransactionResponse {

    private Date transactionDate;
    private String fromCardNumber;
    private String toCardNumber;
    private String toPhoneNumber;
    private String transactionType;
    private Double transactionAmount;
    private Double balanceAfterTransaction;

}
