package it.robertoconterosito.wallet.data.sdk.response;

import it.robertoconterosito.wallet.data.sdk.ResponseCode;
import it.robertoconterosito.wallet.data.sdk.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetWalletResponse  {
    private ResponseCode responseCode;
    private Long id;
    private String name;
    private Money balance;
}
