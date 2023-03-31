package it.robertoconterosito.wallet.event;

import it.robertoconterosito.wallet.data.sdk.event.WalletEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.AbstractApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;

public class RabbitMQApplicationEventMulticaster extends SimpleApplicationEventMulticaster {
    private Logger logger = LoggerFactory.getLogger(RabbitMQApplicationEventMulticaster.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void multicastEvent(ApplicationEvent event) {
        Object payload = getPayload(event);
        if (payload != null ) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.ROUTER_KEY, payload);
        }
        else {
            super.multicastEvent(event);
        }
    }

    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        Object payload = getPayload(event);
        if (payload instanceof WalletEvent) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.ROUTER_KEY, payload);
        }
        else {
            super.multicastEvent(event, eventType);
        }
    }

    private Object getPayload(ApplicationEvent event) {
        if (event instanceof PayloadApplicationEvent<?>) {
            PayloadApplicationEvent<?> payloadApplicationEvent = (PayloadApplicationEvent<?>) event;
            Object payload = payloadApplicationEvent.getPayload();
            if (payload instanceof WalletEvent) {
                return payload;
            }
        }
        return null;
    }
}
