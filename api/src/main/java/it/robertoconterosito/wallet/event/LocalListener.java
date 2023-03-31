package it.robertoconterosito.wallet.event;

import it.robertoconterosito.wallet.data.sdk.event.WalletEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class LocalListener {

    private static final Logger logger = LoggerFactory.getLogger(LocalListener.class);

    @EventListener
    @SuppressWarnings("unused")
    public void handleEvent(WalletEvent event) {
        String className = event.getClass().getSimpleName();
        logger.info("Event received: " + className);
    }
}
