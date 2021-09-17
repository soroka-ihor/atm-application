package io.codelions.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Transactions {
    private String message;
    private List<TransactionResponse> transactions;
}
