package io.codelions.mappers;

import io.codelions.dto.TransactionResponse;
import io.codelions.entities.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionListToTransactionResponseList {

    public static List<TransactionResponse> map(List<Transaction> transactions) {
        List<TransactionResponse> transactionResponses = new ArrayList<>();

        transactions.forEach(
                transaction -> transactionResponses.add(TransactionToTransactionResponse.map(transaction))
        );

        return transactionResponses;
    }
}
