package it.robertoconterosito.wallet.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQRunner {
    private final RabbitTemplate rabbitTemplate;

}
