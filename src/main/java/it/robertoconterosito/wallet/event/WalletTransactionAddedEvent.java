package it.robertoconterosito.wallet.event;

import it.robertoconterosito.wallet.domain.Wallet;
import it.robertoconterosito.wallet.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WalletTransactionAddedEvent extends WalletEvent {
    private Wallet wallet;
    private Transaction transaction;
}
