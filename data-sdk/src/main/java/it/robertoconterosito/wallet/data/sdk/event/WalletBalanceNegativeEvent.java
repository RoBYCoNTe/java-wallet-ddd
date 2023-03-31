package it.robertoconterosito.wallet.data.sdk.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalletBalanceNegativeEvent extends WalletEvent {
    private Long id;

}
