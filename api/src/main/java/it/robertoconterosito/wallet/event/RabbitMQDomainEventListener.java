package it.robertoconterosito.wallet.event;

import it.robertoconterosito.wallet.data.sdk.event.WalletEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

public class RabbitMQDomainEventListener implements ApplicationListener<PayloadApplicationEvent<?>>
{
    private Logger logger = LoggerFactory.getLogger(RabbitMQDomainEventListener.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQDomainEventListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onApplicationEvent(PayloadApplicationEvent<?> event) {
        Object payload = event.getPayload();
        if (payload instanceof WalletEvent) {
            logger.info("Received local event: " + payload);
            rabbitTemplate.convertAndSend(RabbitMQConfig.ROUTER_KEY, payload);
        }
    }
}
