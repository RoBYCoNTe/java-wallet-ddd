package tests.service;

import it.robertoconterosito.wallet.Application;
import it.robertoconterosito.wallet.data.domain.Wallet;
import it.robertoconterosito.wallet.data.domain.repository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
@EnableWebMvc
@ComponentScan(basePackages = {
        "it.robertoconterosito.wallet",
        "it.robertoconterosito.wallet.data",
})
public class WalletServiceImplTests {
    @Autowired
    private WalletRepository walletRepository;

    @Test
    @DisplayName("Test WalletRepository is not null")
    public void assertRepositoryNotNull() {
        assertNotNull(walletRepository);
    }

    @Test
    @DisplayName("Test WalletRepository is not null")
    public void testDomainPendingEvents() {
        Wallet wallet = Wallet.create("My Wallet");
        assertTrue(wallet.getPendingEvents().size() > 0);

        walletRepository.save(wallet);
        assertTrue(wallet.getPendingEvents().size() == 0);
    }
}
