package io.codelions.security;

import io.codelions.entities.Card;
import io.codelions.repositories.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CardRepository cardRepository;

    @Override
    public UserDetails loadUserByUsername(String cardNumber) throws UsernameNotFoundException {
        Card card = cardRepository.findByNumber(Long.valueOf(cardNumber));

        return new User(card.getNumber().toString(), card.getPinCode().toString(), emptyList());
    }
}