package io.codelions.mappers;

import io.codelions.dto.TransactionResponse;
import io.codelions.entities.Transaction;

public class TransactionToTransactionResponse {
    public static TransactionResponse map(Transaction transaction) {
        return TransactionResponse.builder()
                .fromCardNumber(transaction.getFromCard().getNumber().toString())
                .toCardNumber(transaction.getToCard())
                .transactionAmount(transaction.getTransactionAmount())
                .balanceAfterTransaction(transaction.getBalanceAfterTransaction())
                .transactionDate(transaction.getTransactionDate())
                .transactionType(transaction.getTransactionType())
                .build();
    }
}
