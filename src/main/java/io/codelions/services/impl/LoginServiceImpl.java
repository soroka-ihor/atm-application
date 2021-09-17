package io.codelions.services.impl;

import io.codelions.dto.LoginRequest;
import io.codelions.dto.LoginResponse;
import io.codelions.exceptions.CardExpiredException;
import io.codelions.exceptions.CardNotFoundException;
import io.codelions.repositories.CardRepository;
import io.codelions.services.JwtService;
import io.codelions.services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final CardRepository cardRepository;
    private final JwtService jwt;

    @Override
    public <T> T login(LoginRequest loginRequest) {

        var card = cardRepository.findByNumber(Long.valueOf(loginRequest.getCardNumber()));

        if (card == null)
            throw new CardNotFoundException("Card number " + loginRequest.getCardNumber() + " is not found.");
        if (isExpired(card.getValidity()))
            throw new CardExpiredException(String.format("Card number %d is expired.", card.getNumber()));

        if (BCrypt.checkpw(loginRequest.getPinCode(), card.getPinCode())) {
            return (T) LoginResponse.builder()
                    .accessToken(jwt.generateToken(card, "ACCESS"))
                    .build();
        }

        return null;
    }

    private boolean isExpired(Long date) {
        var expire = new Date(date);
        return new Date().after(new Date(date));
    }

}
