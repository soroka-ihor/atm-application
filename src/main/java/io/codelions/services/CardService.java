package io.codelions.services;

import io.codelions.dto.BaseResponse;
import io.codelions.dto.CreateCardRequest;
import io.codelions.dto.Transactions;
import io.codelions.dto.WithdrawCashResponse;
import io.codelions.entities.Card;

public interface CardService {
    /**
     * Saves a card into database.
     * @param createCardRequest object that 
     * @return
     */
    BaseResponse registerCard(CreateCardRequest createCardRequest);
    /**
     *
     * @param amount
     * @param cardNumber
     * @return
     */
    WithdrawCashResponse withdrawCash(Integer amount, String cardNumber) throws Exception;
    /**
     *
     * @param cardNumber
     * @param phoneNumber
     * @param money
     * @return
     */
    BaseResponse topUpPhoneNumber(String cardNumber, String phoneNumber, Double money);

    BaseResponse transferMoneyToCard(String from, String to, Double amountOfMoney);

    void pullMoney(String cardNumber, Double amountOfMoney);

    BaseResponse getBalance(String cardNumber);

    Transactions getTransactions(String cardNumber);
}
