package it.robertoconterosito.wallet.event;

import it.robertoconterosito.wallet.domain.Wallet;
import lombok.Getter;

@Getter
public class WalletBalanceNegativeEvent {
    private Wallet wallet;
    public WalletBalanceNegativeEvent(Wallet wallet) {
        this.wallet = wallet;
    }


}
