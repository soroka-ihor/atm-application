package io.codelions.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.codelions.dto.*;
import io.codelions.services.CardService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/withdraw")
    @SneakyThrows
    public ResponseEntity<WithdrawCashResponse> withdraw(@RequestBody WithdrawCashRequest request) {
        return ResponseEntity.ok(cardService.withdrawCash(request.getAmount(), request.getCardNumber()));
    }

    @GetMapping("/balance/{cardNumber}")
    public ResponseEntity<BaseResponse> getBalance(@PathVariable String cardNumber) {
        return ResponseEntity.ok(cardService.getBalance(cardNumber));
    }

    @GetMapping("/pullMoney")
    public ResponseEntity<BaseResponse> pullMoney(@RequestParam String card, @RequestParam Double amount) {
        cardService.pullMoney(card, amount);
        return ResponseEntity.ok(
                BaseResponse.builder()
                    .message("You add to card #" + card + " for amount " + amount)
                    .status(200)
                    .build()
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<BaseResponse> transferMoney(@RequestBody TransferMoneyRequest request) {
        return ResponseEntity.ok(
                cardService.transferMoneyToCard(request.getFrom(), request.getTo(), request.getAmountOfMoney())
        );
    }

    @GetMapping("/transactions")
    public ResponseEntity<Transactions> getTransactions(@RequestParam String cardNumber) {
        return ResponseEntity.ok(cardService.getTransactions(cardNumber));
    }
}
