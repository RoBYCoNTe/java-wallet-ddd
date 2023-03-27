package it.robertoconterosito.wallet.data.sdk.response;

import it.robertoconterosito.wallet.data.sdk.ResponseCode;
import it.robertoconterosito.wallet.data.sdk.model.TransactionItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetWalletTransactionListResponse extends PaginatedResponse<TransactionItem> {
    private ResponseCode responseCode;
}
