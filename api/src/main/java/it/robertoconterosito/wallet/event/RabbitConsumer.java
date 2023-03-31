package it.robertoconterosito.wallet.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unused")
public class RabbitConsumer {
    private Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);

    @RabbitListener()
    public void receiveMessage(String message) {
        logger.info("Received message: " + message);
    }
}
