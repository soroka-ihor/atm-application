package io.codelions.exceptions;

import io.codelions.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(CardExistException.class)
    public ResponseEntity<BaseResponse> handleCardExistException(CardExistException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.builder()
                .status(400)
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build()
        );
    }

    @ExceptionHandler(CardExpiredException.class)
    public ResponseEntity<BaseResponse> handleCardExpiredException(CardExpiredException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.builder()
                        .status(400)
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<BaseResponse> handleCardNotFoundException(CardNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                BaseResponse.builder()
                        .status(404)
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(NoMoneyInAtmException.class)
    public ResponseEntity<BaseResponse> noMoneyInAtmException(NoMoneyInAtmException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.builder()
                        .status(400)
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<BaseResponse> notEnoughMoneyException(NotEnoughMoneyException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.builder()
                        .status(400)
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(NotMultipleAmountRequestedException.class)
    public ResponseEntity<BaseResponse> notMultipleAmountRequestedException(NotMultipleAmountRequestedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.builder()
                        .status(400)
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> exception(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                BaseResponse.builder()
                        .status(400)
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .message(exception.getMessage())
                        .build()
        );
    }

}
