package com.frozendo.rabbit.sample.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.frozendo.rabbit.sample.domain.enums.TopicEnum.DEPARTMENT_BINDING_KEY;
import static com.frozendo.rabbit.sample.domain.enums.TopicEnum.DEPARTMENT_PROMOTION_BINDING_KEY;
import static com.frozendo.rabbit.sample.domain.enums.TopicEnum.DEPARTMENT_SPORT_BINDING_KEY;
import static com.frozendo.rabbit.sample.domain.enums.TopicEnum.PRODUCT_REGISTER_QUEUE;
import static com.frozendo.rabbit.sample.domain.enums.TopicEnum.PROMOTION_QUEUE;
import static com.frozendo.rabbit.sample.domain.enums.TopicEnum.SPORT_DEPARTMENT_QUEUE;
import static com.frozendo.rabbit.sample.domain.enums.TopicEnum.SPRING_TOPIC_PRODUCT_EX;

@Configuration
public class TopicExchangeConfig {

    private final ConnectionFactory connectionFactory;

    public TopicExchangeConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void createTopicElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createExchange(rabbitAdmin);
        createQueues(rabbitAdmin);
        doBinding(rabbitAdmin);
    }

    private void createExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .topicExchange(SPRING_TOPIC_PRODUCT_EX.getValue())
                .build());
    }

    private void createQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PRODUCT_REGISTER_QUEUE.getValue())
                .build());
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SPORT_DEPARTMENT_QUEUE.getValue())
                .build());
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PROMOTION_QUEUE.getValue())
                .build());
    }

    private void doBinding(RabbitAdmin rabbitAdmin) {
        var productRegisterBinding = new Binding(PRODUCT_REGISTER_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_TOPIC_PRODUCT_EX.getValue(),
                DEPARTMENT_BINDING_KEY.getValue(),
                null);
        var sportDepartmentBinding = new Binding(SPORT_DEPARTMENT_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_TOPIC_PRODUCT_EX.getValue(),
                DEPARTMENT_SPORT_BINDING_KEY.getValue(),
                null);
        var promotionBinding = new Binding(PROMOTION_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                SPRING_TOPIC_PRODUCT_EX.getValue(),
                DEPARTMENT_PROMOTION_BINDING_KEY.getValue(),
                null);

        rabbitAdmin.declareBinding(productRegisterBinding);
        rabbitAdmin.declareBinding(sportDepartmentBinding);
        rabbitAdmin.declareBinding(promotionBinding);
    }
}
