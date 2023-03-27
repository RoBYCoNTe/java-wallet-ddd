package it.robertoconterosito.wallet.data.application;

import it.robertoconterosito.wallet.data.domain.Transaction;
import it.robertoconterosito.wallet.data.domain.Wallet;
import it.robertoconterosito.wallet.data.domain.repository.WalletRepository;
import it.robertoconterosito.wallet.data.sdk.ResponseCode;
import it.robertoconterosito.wallet.data.sdk.WalletService;
import it.robertoconterosito.wallet.data.sdk.model.TransactionItem;
import it.robertoconterosito.wallet.data.sdk.request.AddTransactionRequest;
import it.robertoconterosito.wallet.data.sdk.request.CreateWalletRequest;
import it.robertoconterosito.wallet.data.sdk.request.PaginatedRequest;
import it.robertoconterosito.wallet.data.sdk.response.AddTransactionResponse;
import it.robertoconterosito.wallet.data.sdk.response.CreateWalletResponse;
import it.robertoconterosito.wallet.data.sdk.response.GetWalletResponse;
import it.robertoconterosito.wallet.data.sdk.response.GetWalletTransactionListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public CreateWalletResponse create(CreateWalletRequest request) {
        Wallet wallet = Wallet.create(request.getName());
        walletRepository.save(wallet);

        return new CreateWalletResponse(ResponseCode.OK, wallet.getId());
    }

    @Override
    public GetWalletResponse get(Long id) {
        Wallet wallet = walletRepository.findById(id).orElse(null);
        if (wallet == null) {
            return new GetWalletResponse(ResponseCode.WALLET_NOT_FOUND, null, null, null);
        }
        return new GetWalletResponse(ResponseCode.OK, wallet.getId(), wallet.getName(), wallet.getBalance());
    }

    @Override
    public AddTransactionResponse addTransaction(AddTransactionRequest request) {
        Wallet wallet = walletRepository.findById(request.getWalletId()).orElse(null);
        if (wallet == null) {
            return new AddTransactionResponse(ResponseCode.WALLET_NOT_FOUND, null, null);
        }
        Transaction transaction = Transaction.create(
                request.getType(),
                request.getAmount(),
                request.getDescription()
        );
        wallet.addTransaction(transaction);
        walletRepository.save(wallet);

        return new AddTransactionResponse(ResponseCode.OK, transaction.getId(), wallet.getBalance());
    }

    @Override
    public GetWalletTransactionListResponse getTransactions(Long walletId, PaginatedRequest request) {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet == null) {
            return new GetWalletTransactionListResponse(ResponseCode.WALLET_NOT_FOUND);
        }
        List<Transaction> paginatedResults = wallet.getTransactions().subList(request.getFrom(), request.getTo());
        GetWalletTransactionListResponse response = new GetWalletTransactionListResponse(ResponseCode.OK);
        List<TransactionItem> data = new ArrayList<>();
        for (Transaction transaction : paginatedResults) {
            TransactionItem item = new TransactionItem();
            item.setId(transaction.getId());
            item.setAmount(transaction.getAmount());
            item.setDescription(transaction.getDescription());
            item.setType(transaction.getType());
            data.add(item);
        }
        response.setData(data);
        response.setTotal(wallet.getTransactions().size());

        return response;
    }
}
