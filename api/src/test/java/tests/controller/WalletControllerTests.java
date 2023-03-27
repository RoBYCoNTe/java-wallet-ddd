package tests.controller;

import it.robertoconterosito.wallet.data.sdk.model.Money;
import it.robertoconterosito.wallet.data.sdk.model.TransactionType;
import it.robertoconterosito.wallet.data.sdk.request.AddTransactionRequest;
import it.robertoconterosito.wallet.data.sdk.request.CreateWalletRequest;
import it.robertoconterosito.wallet.data.sdk.request.PaginatedRequest;
import it.robertoconterosito.wallet.data.sdk.response.AddTransactionResponse;
import it.robertoconterosito.wallet.data.sdk.response.CreateWalletResponse;
import it.robertoconterosito.wallet.data.sdk.response.GetWalletResponse;
import it.robertoconterosito.wallet.data.sdk.response.GetWalletTransactionListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootConfiguration
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {
        "it.robertoconterosito.wallet",
        "it.robertoconterosito.wallet.data",
})
public class WalletControllerTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    @DisplayName("Should create a valid wallet")
    public void testCreateWallet() {

        CreateWalletRequest createWalletRequest = new CreateWalletRequest("My wallet");
        ResponseEntity<CreateWalletResponse> createWalletResponse = restTemplate.postForEntity(
                "/wallets",
                createWalletRequest,
                CreateWalletResponse.class);

        assertNotNull(createWalletResponse);
        assertEquals(HttpStatus.OK, createWalletResponse.getStatusCode());
        assertNotNull(createWalletResponse.getBody());

        assertTrue(createWalletResponse.getBody().getId() > 0);
    }

    @Test
    @DisplayName("Should get a valid wallet")
    public void testCreateAndGet() {
        CreateWalletRequest createWalletRequest = new CreateWalletRequest("My wallet");
        ResponseEntity<CreateWalletResponse> createWalletResponse = restTemplate.postForEntity(
                "/wallets",
                createWalletRequest,
                CreateWalletResponse.class);

        assertNotNull(createWalletResponse);
        assertEquals(HttpStatus.OK, createWalletResponse.getStatusCode());

        ResponseEntity<GetWalletResponse> getResponse = restTemplate.getForEntity(
                "/wallets/" + createWalletResponse.getBody().getId(),
                GetWalletResponse.class);

        assertNotNull(getResponse);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        assertEquals(createWalletResponse.getBody().getId(), getResponse.getBody().getId());
        assertEquals("My wallet", getResponse.getBody().getName());
    }

    @Test
    @DisplayName("Should create a wallet and add a transaction")
    public void testCreateAndAddTransaction() {
        CreateWalletRequest createWalletRequest = new CreateWalletRequest("My wallet");
        ResponseEntity<CreateWalletResponse> createWalletResponse = restTemplate.postForEntity(
                "/wallets",
                createWalletRequest,
                CreateWalletResponse.class);

        Money amount = Money.of(Currency.getInstance("EUR"), BigDecimal.valueOf(100.25));
        AddTransactionRequest addTransactionRequest = new AddTransactionRequest(
                createWalletResponse.getBody().getId(),
                amount,
                "Ice cream",
                TransactionType.OUTCOME);
        ResponseEntity<AddTransactionResponse> addTransactionResponse = restTemplate.postForEntity(
                "/wallets/" + createWalletResponse.getBody().getId() + "/transactions",
                addTransactionRequest,
                AddTransactionResponse.class);

        assertNotNull(addTransactionRequest);
        assertEquals(HttpStatus.OK, addTransactionResponse.getStatusCode());

        ResponseEntity<GetWalletResponse> getResponse = restTemplate.getForEntity(
                "/wallets/" + createWalletResponse.getBody().getId(),
                GetWalletResponse.class);
        assertEquals(BigDecimal.valueOf(-100.25), getResponse.getBody().getBalance().getAmount());
    }

    @Test
    @DisplayName("Consume paginated list of transactions")
    public void testTransactionPagination() {
        CreateWalletRequest createWalletRequest = new CreateWalletRequest("My wallet");
        ResponseEntity<CreateWalletResponse> createWalletResponse = restTemplate.postForEntity(
                "/wallets",
                createWalletRequest,
                CreateWalletResponse.class);

        for (int i = 0; i < 30; i++) {
            Money amount = Money.of(Currency.getInstance("EUR"), BigDecimal.valueOf(100.25));
            String description = String.format("Ice cream %d", i);
            AddTransactionRequest addTransactionRequest = new AddTransactionRequest(
                    createWalletResponse.getBody().getId(),
                    amount,
                    description,
                    TransactionType.OUTCOME);
            ResponseEntity<AddTransactionResponse> addTransactionResponse = restTemplate.postForEntity(
                    "/wallets/" + createWalletResponse.getBody().getId() + "/transactions",
                    addTransactionRequest,
                    AddTransactionResponse.class);
            assertEquals(HttpStatus.OK, addTransactionResponse.getStatusCode());
        }

        PaginatedRequest paginatedRequest = new PaginatedRequest(1, 10);
        String url = String.format("/wallets/%d/transactions?page={page}&size={size}", createWalletResponse.getBody().getId());
        ResponseEntity<GetWalletTransactionListResponse> getWalletTransactionListResponse = restTemplate.getForEntity(
            url,
            GetWalletTransactionListResponse.class,
            paginatedRequest.toMap());

        assertEquals(HttpStatus.OK, getWalletTransactionListResponse.getStatusCode());

        assertEquals(10, getWalletTransactionListResponse.getBody().getData().size());
        assertEquals(30, getWalletTransactionListResponse.getBody().getTotal());

        // Check the name of the first and last transaction
        assertEquals("Ice cream 0", getWalletTransactionListResponse.getBody().getData().get(0).getDescription());
        assertEquals("Ice cream 9", getWalletTransactionListResponse.getBody().getData().get(9).getDescription());

        getWalletTransactionListResponse = restTemplate.getForEntity(
            url,
            GetWalletTransactionListResponse.class,
            paginatedRequest.changePage(2).toMap());

        assertEquals(10, getWalletTransactionListResponse.getBody().getData().size());

        // Check the name of the first and last transaction
        assertEquals("Ice cream 10", getWalletTransactionListResponse.getBody().getData().get(0).getDescription());
        assertEquals("Ice cream 19", getWalletTransactionListResponse.getBody().getData().get(9).getDescription());

    }
}
