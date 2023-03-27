package it.robertoconterosito.wallet.data.domain;

import it.robertoconterosito.wallet.data.sdk.model.Money;
import it.robertoconterosito.wallet.data.sdk.model.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Money amount;

    private String description;

    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @Setter
    private Wallet wallet;

    public static Transaction create(TransactionType type, Money amount, String description) {
        Transaction transaction = new Transaction();
        transaction.type = type;
        transaction.amount = amount;
        transaction.description = description;
        return transaction;
    }

    public static Transaction income(Money money, String description) {
        return create(TransactionType.INCOME, money, description);
    }
    public static Transaction outcome(Money money, String description) {
        return create(TransactionType.OUTCOME, money, description);
    }
}
