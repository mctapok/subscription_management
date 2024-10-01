package ru.gavrilov.payment.service.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "account_id")
    Long accountId;
    @Column(name = "client_id")
    Long clientId;
    @Column(name = "balance")
    BigDecimal balance;

    public Account(Long accountId, Long clientId){
        this.accountId = accountId;
        this.clientId = clientId;
        this.balance = BigDecimal.ZERO;
    }
}
