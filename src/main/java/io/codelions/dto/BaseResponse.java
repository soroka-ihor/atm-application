package io.codelions.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {
    private int status;
    private String message;
    private String error;
}
