package io.codelions.services.impl;

import io.codelions.banknotes.CassetteStorage;
import io.codelions.dto.BaseResponse;
import io.codelions.dto.CreateCardRequest;
import io.codelions.dto.Transactions;
import io.codelions.dto.WithdrawCashResponse;
import io.codelions.entities.Card;
import io.codelions.entities.Customer;
import io.codelions.entities.Transaction;
import io.codelions.enums.TransactionType;
import io.codelions.exceptions.*;
import io.codelions.mappers.TransactionListToTransactionResponseList;
import io.codelions.repositories.CardRepository;
import io.codelions.repositories.CustomerRepository;
import io.codelions.repositories.TransactionRepository;
import io.codelions.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private final CassetteStorage cassetteStorage;
    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public BaseResponse registerCard(CreateCardRequest request) {
        double initBalance = 1000.00;

        if (cardExists(request.getCardNumber()))
            throw new CardExistException(String.format("String number %d is already exist", request.getCardNumber()));

        if (customerNotExists(request.getUsername()))
            throw new CustomerNotExistsException(String.format("Customer %n does not exist.", request.getUsername()));

        Card card = Card.builder()
                .cvv2(request.getCvv2())
                .validity(request.getValidity())
                .balance(initBalance)
                .customer(customerRepository.findByUsername(request.getUsername()))
                .number(Long.valueOf(request.getCardNumber()))
                .pinCode(BCrypt.hashpw(request.getPinCode(), BCrypt.gensalt()))
                .build();

        cardRepository.save(card);

        return BaseResponse.builder()
                .message("Card is successfully registered.")
                .status(200)
                .build();
    }


    @Override
    public WithdrawCashResponse withdrawCash(Integer amount, String cardNumber) throws NoMoneyInAtmException, NotEnoughMoneyException {

        if (!cardExists(cardNumber))
            throw new CardExistException("Card not exist");

        var card = cardRepository.findByNumber(Long.valueOf(cardNumber));
        var availableMoneyInATM = cassetteStorage.availableMoney();
        var minimalAvailableNominal = cassetteStorage.getMinimalAvailableNominal();

        checkIfThereIsEnoughMoney(Double.valueOf(amount), card, availableMoneyInATM, minimalAvailableNominal);

        var banknotes = new HashMap<String, Integer>();

        banknotes = collectCash(amount, banknotes);

        card.setBalance(card.getBalance() - amount);
        cardRepository.save(card);

        saveTransaction(TransactionType.TO_CASH, Double.valueOf(amount), card, null, card.getBalance(), "");

        return WithdrawCashResponse.builder()
                .message(String.format("You've withdrawn money for sum: %d", amount))
                .money(banknotes).build();
    }

    private HashMap<String, Integer> collectCash(Integer amount, HashMap<String, Integer> banknotes) {

        int sum = 0;
        int remainder = 0;
        /*
            Step 1. Find nominal that is lesser or equal to requested amount of money.
         */
        var pickedCassette = Optional.of(cassetteStorage.findLesserOrEqualNominal(amount)).orElseThrow(NotEnoughMoneyException::new);
        var nominal = pickedCassette.getNominal();
        /*
            Step 2. Add the banknote to sum until banknotes are over or sum is bigger than requested amount of money.
         */
        int banknotesCount = 0;
        while (pickedCassette.size() > 0 && sum < amount) {
            sum += pickedCassette.removeBanknote();
            banknotesCount++;
        }
        /*
            Step 3. Calculate remainder.
         */
        if (sum > amount) {
            sum = sum - pickedCassette.getNominal();
            remainder = amount - sum;
            banknotesCount--;
        }
        else if (sum < amount) {
            remainder = Math.abs(sum - amount);
        }
        else {
            remainder = 0;
        }

        banknotes.put(pickedCassette.getStringNominal(), banknotesCount);

        return remainder == 0 ? banknotes : collectCash(remainder, banknotes);
    }
    /**
     * @param cardNumber
     * @param phoneNumber
     * @param money
     * @return
     */
    @Override
    public BaseResponse topUpPhoneNumber(String cardNumber, String phoneNumber, Double money) {
        var card = cardRepository.findByNumber(Long.valueOf(cardNumber));
        return null;
    }

    @Override
    public void pullMoney(String cardNumber, Double amountOfMoney) {
       var card =  cardRepository.findByNumber(Long.valueOf(cardNumber));
       card.setBalance(Double.valueOf(card.getBalance() + amountOfMoney));
       cardRepository.save(card);
       saveTransaction(TransactionType.REPLENISHMENT, amountOfMoney, card, card, card.getBalance(), "");
    }

    @Override
    public BaseResponse getBalance(String cardNumber) {
        return BaseResponse.builder()
                .message("Card balance is " + round(cardRepository.findByNumber(Long.valueOf(cardNumber)).getBalance(), 2))
                .status(200)
                .build();
    }

    @Override
    public Transactions getTransactions(String cardNumber) {

        var card = cardRepository.findByNumber(Long.valueOf(cardNumber));
        var transactions = transactionRepository.findByFromCard(card);

        return Transactions.builder()
                .message("Transaction list for card # " + cardNumber)
                .transactions(TransactionListToTransactionResponseList.map(transactions))
                .build();
    }

    @Override
    public BaseResponse transferMoneyToCard(String from, String to, Double amountOfMoney) {

        var availableMoneyInATM = cassetteStorage.availableMoney();
        var minimalAvailableNominal = cassetteStorage.getMinimalAvailableNominal();

        if (!cardExists(from) || !cardExists(to))
            throw new CardExistException("Card not exist");

        var fromCard = cardRepository.findByNumber(Long.valueOf(from));
        var toCard = cardRepository.findByNumber(Long.valueOf(to));

        checkIfThereIsEnoughMoneyForTransfer(amountOfMoney, fromCard);

        fromCard.setBalance(fromCard.getBalance() - amountOfMoney);
        toCard.setBalance(toCard.getBalance() + amountOfMoney);

        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        saveTransaction(TransactionType.TO_CARD, amountOfMoney, fromCard, toCard, fromCard.getBalance(), "");

        return BaseResponse.builder()
                .message("You successfully transfered from card  " + fromCard.getNumber() + " " + amountOfMoney + " to card " + toCard.getNumber())
                .status(200)
                .build();
    }

    private void checkIfThereIsEnoughMoneyForTransfer(Double amountOfMoney, Card fromCard) {
        if (fromCard.getBalance() < amountOfMoney)
            throw new NotEnoughMoneyException("Card doesn't have enough money for that transfer.");
    }

    private void saveTransaction(TransactionType type, Double amount, Card from, Card to, Double balanceAfter, String phoneNumber) {
        var transaction = Transaction.builder()
                .transactionType(type.toString())
                .transactionAmount(amount)
                .balanceAfterTransaction(balanceAfter)
                .transactionDate(new Date())
                .toCard(to != null ? to.getNumber().toString() : "")
                .fromCard(from)
                .toPhoneNumber(phoneNumber)
                .build();
        transactionRepository.save(transaction);
    }

    private void checkIfThereIsEnoughMoney(Double amount, Card card, Integer availableMoneyInATM, Integer minimalAvailableNominal) {
        if (minimalAvailableNominal == 0)
            throw new NoMoneyInAtmException("ATM does not have money");

        if (amount % minimalAvailableNominal != 0)
            throw new NotMultipleAmountRequestedException("Requested amount is not multiple to  " + minimalAvailableNominal);

        if (availableMoneyInATM < amount)
            throw new NotEnoughMoneyException("Not enough money in ATM.");

        if (amount > card.getBalance())
            throw new NotEnoughMoneyException("Not enough money in card balance.");
    }

    private boolean customerNotExists(String username) {
        return customerRepository.findByUsername(username) == null;
    }

    private boolean cardExists(String cardNumber) {
        return cardRepository.findByNumber(Long.valueOf(cardNumber)) != null;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
