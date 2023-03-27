package it.robertoconterosito.wallet.data.sdk.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WalletTransactionAddedEvent extends WalletEvent {
    private Long id;
    private Long transactionId;
}
