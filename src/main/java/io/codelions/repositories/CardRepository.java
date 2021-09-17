package io.codelions.repositories;

import io.codelions.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByNumber(Long cardNumber);
}
