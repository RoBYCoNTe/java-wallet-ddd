package it.robertoconterosito.wallet.event;

import it.robertoconterosito.wallet.data.sdk.event.WalletCreatedEvent;
import it.robertoconterosito.wallet.data.sdk.event.WalletEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class RabbitConsumer {
    private Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);

    @EventListener
    public void handleLocal(WalletCreatedEvent walletEvent) {
        logger.info("Received local event: " + walletEvent);
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleRabbit(WalletCreatedEvent walletEvent) {
        logger.info("Received rabbit event: " + walletEvent);
    }
}
