package it.robertoconterosito.wallet.tests.service;

import it.robertoconterosito.wallet.domain.Wallet;
import it.robertoconterosito.wallet.domain.Money;
import it.robertoconterosito.wallet.domain.Transaction;
import it.robertoconterosito.wallet.event.WalletBalanceNegativeEvent;
import it.robertoconterosito.wallet.exception.WalletNotFoundException;
import it.robertoconterosito.wallet.service.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WalletServiceTests {
    @Autowired
    private WalletService walletService;


    @Test
    @DisplayName("Should create a valid wallet")
    public void testCreateWallet() {
        Wallet wallet = walletService.createWallet("My wallet");
        assertNotNull(wallet);
        assertTrue(wallet.getId() > 0);
    }

    @Test
    @DisplayName("Test domain events")
    public void testDomainEvents() {
        Wallet wallet = walletService.createWallet("My wallet");
        Transaction income = Transaction.income(Money.of(Currency.getInstance("EUR"), BigDecimal.valueOf(3000.55)), "Salary");

        wallet.addTransaction(income);
        assertEquals(1, wallet.getPendingEvents().size());

        walletService.persist(wallet);

        assertEquals(0, wallet.getPendingEvents().size());
    }

    @Test
    @DisplayName("Test negative balance event")
    public void testNegativeBalance() {
        Wallet wallet = walletService.createWallet("My wallet");
        Transaction outcome = Transaction.outcome(Money.of(Currency.getInstance("EUR"), BigDecimal.valueOf(1000.00)), "Rent");

        wallet.addTransaction(outcome);
        Object event = wallet.getPendingEvents()
                .stream()
                .findFirst()
                .orElse(null);

        assertNotNull(event);
        assertTrue(event instanceof WalletBalanceNegativeEvent);

        walletService.persist(wallet);

        assertEquals(0, wallet.getPendingEvents().size());
    }

    @Test
    @DisplayName("Test add income")
    public void testAddIncome() throws WalletNotFoundException {
        Wallet wallet = walletService.createWallet("My wallet");
        Transaction income = Transaction.income(Money.of(Currency.getInstance("EUR"), BigDecimal.valueOf(3000.55)), "Salary");

        wallet.addTransaction(income);
        walletService.persist(wallet);

        wallet = walletService.get(wallet.getId());
        assertEquals(1, wallet.getTransactions().size());
        assertEquals(BigDecimal.valueOf(3000.55), wallet.getBalance().getAmount());

    }

    @Test
    @DisplayName("Test add outcome")
    public void testAddOutcome() throws WalletNotFoundException {
        Wallet wallet = walletService.createWallet("My wallet");
        Transaction outcome = Transaction.outcome(Money.of(Currency.getInstance("EUR"), BigDecimal.valueOf(1000.25)), "Rent");

        wallet.addTransaction(outcome);
        walletService.persist(wallet);

        wallet = walletService.get(wallet.getId());
        assertEquals(1, wallet.getTransactions().size());
        assertEquals(BigDecimal.valueOf(-1000.25), wallet.getBalance().getAmount());
    }

    @Test
    @DisplayName("Test income/outcome together")
    public void testIncomeOutcomeTogether() throws WalletNotFoundException {
        Wallet wallet = walletService.createWallet("My wallet");
        Transaction income = Transaction.income(Money.of(Currency.getInstance("EUR"), BigDecimal.valueOf(3000.55)), "Salary");

        wallet.addTransaction(income);
        walletService.persist(wallet);
        wallet = walletService.get(wallet.getId());

        assertEquals(1, wallet.getTransactions().size());
        assertEquals(BigDecimal.valueOf(3000.55), wallet.getBalance().getAmount());

        Transaction outcome = Transaction.outcome(Money.of(Currency.getInstance("EUR"), BigDecimal.valueOf(1000.00)), "Rent");
        wallet.addTransaction(outcome);
        walletService.persist(wallet);

        wallet = walletService.get(wallet.getId());
        assertEquals(2, wallet.getTransactions().size());
        assertEquals(BigDecimal.valueOf(2000.55), wallet.getBalance().getAmount());
    }

}
