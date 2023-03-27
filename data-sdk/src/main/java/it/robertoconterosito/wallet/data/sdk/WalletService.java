package it.robertoconterosito.wallet.data.sdk;

import it.robertoconterosito.wallet.data.sdk.request.AddTransactionRequest;
import it.robertoconterosito.wallet.data.sdk.request.CreateWalletRequest;
import it.robertoconterosito.wallet.data.sdk.request.PaginatedRequest;
import it.robertoconterosito.wallet.data.sdk.response.AddTransactionResponse;
import it.robertoconterosito.wallet.data.sdk.response.CreateWalletResponse;
import it.robertoconterosito.wallet.data.sdk.response.GetWalletResponse;
import it.robertoconterosito.wallet.data.sdk.response.GetWalletTransactionListResponse;

public interface WalletService {
    CreateWalletResponse create(CreateWalletRequest request);

    GetWalletResponse get(Long id);

    AddTransactionResponse addTransaction(AddTransactionRequest request);

    GetWalletTransactionListResponse getTransactions(Long walletId, PaginatedRequest request);
}
