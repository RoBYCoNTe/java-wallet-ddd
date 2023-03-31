package it.robertoconterosito.wallet.event;

import it.robertoconterosito.wallet.data.sdk.event.WalletEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class EventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @TransactionalEventListener
    @SuppressWarnings("unused")
    public void handleEvent(WalletEvent event) {
        logger.info("Event received: " + event.toString());
        rabbitTemplate.convertAndSend("wallet", event);
    }
}
