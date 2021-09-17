package io.codelions.repositories;

import io.codelions.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT customer FROM Customer customer " +
            "INNER JOIN " +
            "Card card ON card.customer WHERE card.number = cardNumber", nativeQuery = true)
    Customer findByCardNumber(@Param("cardNumber") String cardNumber);

    Customer findByUsername(String username);
}
