package it.robertoconterosito.wallet.data.sdk.response;

import it.robertoconterosito.wallet.data.sdk.ResponseCode;
import it.robertoconterosito.wallet.data.sdk.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddTransactionResponse {
    private ResponseCode responseCode;
    private Long id;
    /**
     * The new balance of the wallet after the transaction has been added.
     */
    private Money balance;
}
