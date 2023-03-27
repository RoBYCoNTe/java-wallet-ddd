package it.robertoconterosito.wallet.controller.response;

import it.robertoconterosito.wallet.domain.Money;
import it.robertoconterosito.wallet.domain.Transaction;
import it.robertoconterosito.wallet.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetWalletTransactionListResponse extends PaginatedResponse<GetWalletTransactionItemResponse> {


    public GetWalletTransactionListResponse(List<Transaction> list, int from, int to) {
        this.setTotal(list.size());
        this.setData(list.subList(from, to).stream().map(this::map).toList());
    }

    public GetWalletTransactionItemResponse map(Transaction transaction) {
        GetWalletTransactionItemResponse transactionResponse = new GetWalletTransactionItemResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setType(transaction.getType());
        return transactionResponse;
    }
}
