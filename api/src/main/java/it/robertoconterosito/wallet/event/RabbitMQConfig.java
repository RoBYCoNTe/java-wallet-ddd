package it.robertoconterosito.wallet.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    private Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);
    public static final String QUEUE_NAME = "wallet";
    public static final String TOPIC_EXCHANGE_NAME = "wallet";
    public static final String ROUTER_KEY = "wallet";

    @Value("${spring.rabbitmq.host}")
    private String rabbitMQHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitMQPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitMQUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitMQPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMQHost, rabbitMQPort);
        connectionFactory.setUsername(rabbitMQUsername);
        connectionFactory.setPassword(rabbitMQPassword);
        logger.info("RabbitMQ connection factory created");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        logger.info("RabbitMQ template created");
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        logger.info("RabbitMQ message converter created");
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue() {
        logger.info("RabbitMQ queue created");
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        logger.info("RabbitMQ exchange created");
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        logger.info("RabbitMQ binding created");
        return BindingBuilder.bind(queue).to(exchange).with(ROUTER_KEY);
    }

    @Bean
    public ApplicationEventPublisher applicationEventPublisher(RabbitTemplate rabbitTemplate) {
        logger.info("RabbitMQ event publisher created");
        return new RabbitMQEventPublisher(rabbitTemplate, TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public RabbitMQApplicationEventMulticaster applicationEventMulticaster() {
        logger.info("RabbitMQ event multicaster created");
        return new RabbitMQApplicationEventMulticaster();
    }
}
