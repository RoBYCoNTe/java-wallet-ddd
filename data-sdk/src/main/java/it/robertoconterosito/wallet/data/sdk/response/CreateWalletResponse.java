package it.robertoconterosito.wallet.data.sdk.response;

import it.robertoconterosito.wallet.data.sdk.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateWalletResponse {
    private ResponseCode responseCode;
    private Long id;

}
