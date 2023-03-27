package it.robertoconterosito.wallet.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class WalletEventListener {

    @Async
    @TransactionalEventListener
    @SuppressWarnings("unused")
    public void handle(WalletEvent evt) {
        if (evt instanceof WalletTransactionAddedEvent) {
            WalletTransactionAddedEvent event = (WalletTransactionAddedEvent) evt;
            assert event.getWallet() != null;
        }

    }
}
