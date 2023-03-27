package it.robertoconterosito.wallet.data.sdk.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateWalletRequest {
    private String name;
}
