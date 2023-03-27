package it.robertoconterosito.wallet.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class Money {
    private final BigDecimal amount;
    private final Currency currency;

    public Money() {
        this.amount = BigDecimal.ZERO;
        this.currency = Currency.getInstance("EUR");
    }

    public Money(BigDecimal amount, Currency currency) {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("Amount and currency cannot be null");
        }

        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot add money with different currencies");
        }
        BigDecimal newAmount = amount.add(money.amount);
        return new Money(newAmount, currency);
    }

    public Money subtract(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot subtract money with different currencies");
        }

        BigDecimal newAmount = amount.subtract(money.amount);
        return new Money(newAmount, currency);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Money)) {
            return false;
        }

        Money other = (Money)obj;
        return amount.equals(other.amount) && currency.equals(other.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

    public static Money of(Currency currency, BigDecimal amount) {
        return new Money(amount, currency);
    }
}
