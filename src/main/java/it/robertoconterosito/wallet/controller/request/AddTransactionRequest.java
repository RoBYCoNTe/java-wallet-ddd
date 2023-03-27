package it.robertoconterosito.wallet.controller.request;

import it.robertoconterosito.wallet.domain.Money;
import it.robertoconterosito.wallet.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddTransactionRequest {

    private Money amount;
    private String description;

    private TransactionType type;


}
