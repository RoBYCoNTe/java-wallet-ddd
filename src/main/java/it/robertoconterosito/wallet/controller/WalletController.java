package it.robertoconterosito.wallet.controller;

import it.robertoconterosito.wallet.controller.request.AddTransactionRequest;
import it.robertoconterosito.wallet.controller.request.CreateWalletRequest;
import it.robertoconterosito.wallet.controller.request.PaginatedRequest;
import it.robertoconterosito.wallet.controller.response.AddTransactionResponse;
import it.robertoconterosito.wallet.controller.response.CreateWalletResponse;
import it.robertoconterosito.wallet.controller.response.GetWalletResponse;
import it.robertoconterosito.wallet.controller.response.GetWalletTransactionListResponse;
import it.robertoconterosito.wallet.domain.Transaction;
import it.robertoconterosito.wallet.domain.Wallet;
import it.robertoconterosito.wallet.exception.WalletNotFoundException;
import it.robertoconterosito.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/wallets")
    public ResponseEntity<CreateWalletResponse> add(@RequestBody CreateWalletRequest request) {
        Wallet wallet = walletService.createWallet(request.getName());
        return ResponseEntity.ok(new CreateWalletResponse(wallet.getId()));
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<GetWalletResponse> get(@PathVariable Long id) {
        try {
            Wallet wallet = walletService.get(id);
            return ResponseEntity.ok(new GetWalletResponse(wallet));
        }
        catch (WalletNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/wallets/{id}/transactions")
    public ResponseEntity<AddTransactionResponse> addTransaction(@PathVariable Long id, @RequestBody AddTransactionRequest request) {
        try {
            Wallet wallet = walletService.get(id);
            Transaction transaction = Transaction.create(
                request.getType(),
                request.getAmount(),
                request.getDescription()
            );
            wallet.addTransaction(transaction);
            walletService.persist(wallet);

            return ResponseEntity.ok(new AddTransactionResponse(transaction.getId(), wallet.getBalance()));
        }
        catch (WalletNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/wallets/{id}/transactions")
    public ResponseEntity<GetWalletTransactionListResponse> getTransactions(
            @PathVariable Long id,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        try {
            PaginatedRequest request = new PaginatedRequest(page, size);
            Wallet wallet = walletService.get(id);
            return ResponseEntity.ok(new GetWalletTransactionListResponse(
                wallet.getTransactions(),
                request.getFrom(),
                request.getTo()));
        }
        catch (WalletNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
