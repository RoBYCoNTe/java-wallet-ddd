package it.robertoconterosito.wallet.domain;

import it.robertoconterosito.wallet.event.WalletBalanceNegativeEvent;
import it.robertoconterosito.wallet.event.WalletTransactionAddedEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Wallet extends DomainEntity<Wallet> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Money balance = Money.of(Currency.getInstance("EUR"), BigDecimal.ZERO);

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();

    public Transaction addTransaction(Transaction transaction) {
        transaction.setWallet(this);

        this.transactions.add(transaction);
        if (transaction.getType() == TransactionType.INCOME) {
            this.setBalance(this.balance.add(transaction.getAmount()));
        }
        else {
            this.setBalance(this.balance.subtract(transaction.getAmount()));
        }

        // Check if balance is negative:
        if (this.balance.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            this.registerEvent(new WalletBalanceNegativeEvent(this));
        }

        this.registerEvent(new WalletTransactionAddedEvent(this, transaction));

        return transaction;
    }
}
