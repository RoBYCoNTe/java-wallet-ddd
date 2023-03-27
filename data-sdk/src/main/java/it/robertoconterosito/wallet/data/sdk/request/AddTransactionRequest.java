package it.robertoconterosito.wallet.data.sdk.request;

import it.robertoconterosito.wallet.data.sdk.model.Money;
import it.robertoconterosito.wallet.data.sdk.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddTransactionRequest {
    private Long walletId;
    private Money amount;
    private String description;
    private TransactionType type;
}
