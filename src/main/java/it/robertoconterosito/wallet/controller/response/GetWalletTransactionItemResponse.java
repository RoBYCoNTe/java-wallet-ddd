package it.robertoconterosito.wallet.controller.response;

import it.robertoconterosito.wallet.domain.Money;
import it.robertoconterosito.wallet.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public final class GetWalletTransactionItemResponse {
    private Long id;
    private String description;
    private Money amount;
    private TransactionType type;
}
