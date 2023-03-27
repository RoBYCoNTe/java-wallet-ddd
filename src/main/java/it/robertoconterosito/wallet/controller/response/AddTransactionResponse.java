package it.robertoconterosito.wallet.controller.response;

import it.robertoconterosito.wallet.domain.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddTransactionResponse extends BaseResponse {
    private Long id;

    /**
     * The new balance of the wallet after the transaction has been added.
     */
    private Money balance;
}
