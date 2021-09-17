package io.codelions.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "number")
    private Long number;

    @Column(name = "validity")
    private Long validity;

    @Column(name = "cvv_2")
    private String cvv2;

    @Column(name = "card_balance")
    private Double balance;

    @OneToMany(mappedBy = "fromCard")
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
