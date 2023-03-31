package it.robertoconterosito.wallet.data.sdk.event;

import lombok.Getter;

@Getter
public class WalletCreatedEvent extends WalletEvent {
    private Long walletId;

    public WalletCreatedEvent(Long walletId) {
        this.walletId = walletId;
    }
}
