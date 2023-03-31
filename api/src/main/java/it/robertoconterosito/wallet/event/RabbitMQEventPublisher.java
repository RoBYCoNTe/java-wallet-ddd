package it.robertoconterosito.wallet.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class RabbitMQEventPublisher implements ApplicationEventPublisher {
    private Logger logger = LoggerFactory.getLogger(RabbitMQEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate, String exchangeName) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        logger.info("Publishing event: " + event);
        rabbitTemplate.convertAndSend(exchangeName, event);
    }

    @Override
    public void publishEvent(Object event) {
        logger.info("Publishing event: " + event);
        rabbitTemplate.convertAndSend(exchangeName, event);
    }
}
