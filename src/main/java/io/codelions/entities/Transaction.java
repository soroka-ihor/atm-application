package io.codelions.entities;

import io.codelions.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column
    private Double transactionAmount;

    @ManyToOne
    @JoinColumn(name = "from_card_number_id")
    private Card fromCard;

    @Column(name = "to_card_number")
    private String toCard;

    @Column(name = "to_phone_number")
    private String toPhoneNumber;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "balance_after_transaction")
    private Double balanceAfterTransaction;

}
