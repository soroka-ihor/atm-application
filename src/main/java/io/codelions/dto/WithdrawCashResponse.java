package io.codelions.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class WithdrawCashResponse {
    private String message;
    private Map<String, Integer> money;
}
