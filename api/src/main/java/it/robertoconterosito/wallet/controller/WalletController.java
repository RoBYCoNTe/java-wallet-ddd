package it.robertoconterosito.wallet.controller;

import it.robertoconterosito.wallet.data.sdk.WalletService;
import it.robertoconterosito.wallet.data.sdk.request.AddTransactionRequest;
import it.robertoconterosito.wallet.data.sdk.request.CreateWalletRequest;
import it.robertoconterosito.wallet.data.sdk.request.PaginatedRequest;
import it.robertoconterosito.wallet.data.sdk.response.AddTransactionResponse;
import it.robertoconterosito.wallet.data.sdk.response.CreateWalletResponse;
import it.robertoconterosito.wallet.data.sdk.response.GetWalletResponse;
import it.robertoconterosito.wallet.data.sdk.response.GetWalletTransactionListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SuppressWarnings("unused")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/wallets")
    public ResponseEntity<CreateWalletResponse> add(@RequestBody CreateWalletRequest request) {
        CreateWalletResponse wallet = walletService.create(request);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<GetWalletResponse> get(@PathVariable Long id) {

        GetWalletResponse wallet = walletService.get(id);
        return ResponseEntity.ok(wallet);

    }

    @PostMapping("/wallets/{id}/transactions")
    public ResponseEntity<AddTransactionResponse> addTransaction(@PathVariable Long id, @RequestBody AddTransactionRequest request) {
        AddTransactionResponse transaction = walletService.addTransaction(request);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/wallets/{id}/transactions")
    public ResponseEntity<GetWalletTransactionListResponse> getTransactions(
            @PathVariable Long id,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        PaginatedRequest request = new PaginatedRequest(page, size);
        GetWalletTransactionListResponse wallet = walletService.getTransactions(id, request);
        return ResponseEntity.ok(wallet);
    }
}
