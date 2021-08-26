package com.frozendo.rabbit.multi.config;

import com.frozendo.rabbit.multi.consumer.topic.ProductRegisterConsumerService;
import com.frozendo.rabbit.multi.consumer.topic.PromotionConsumerService;
import com.frozendo.rabbit.multi.consumer.topic.SportDepartmentConsumerService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.frozendo.rabbit.multi.domain.enums.TopicEnum.DEPARTMENT_BINDING_KEY;
import static com.frozendo.rabbit.multi.domain.enums.TopicEnum.DEPARTMENT_PROMOTION_BINDING_KEY;
import static com.frozendo.rabbit.multi.domain.enums.TopicEnum.DEPARTMENT_SPORT_BINDING_KEY;
import static com.frozendo.rabbit.multi.domain.enums.TopicEnum.PRODUCT_REGISTER_QUEUE;
import static com.frozendo.rabbit.multi.domain.enums.TopicEnum.PROMOTION_QUEUE;
import static com.frozendo.rabbit.multi.domain.enums.TopicEnum.SPORT_DEPARTMENT_QUEUE;
import static com.frozendo.rabbit.multi.domain.enums.TopicEnum.SPRING_TOPIC_PRODUCT_EX;

@Configuration
public class TopicExchangeConfig {

    private final ConnectionFactory connectionFactory;
    private final ProductRegisterConsumerService productRegisterConsumerService;
    private final PromotionConsumerService promotionConsumerService;
    private final SportDepartmentConsumerService sportDepartmentConsumerService;

    public TopicExchangeConfig(@Qualifier("topicConnectionFactory") ConnectionFactory connectionFactory,
                               ProductRegisterConsumerService productRegisterConsumerService,
                               PromotionConsumerService promotionConsumerService,
                               SportDepartmentConsumerService sportDepartmentConsumerService) {
        this.connectionFactory = connectionFactory;
        this.productRegisterConsumerService = productRegisterConsumerService;
        this.promotionConsumerService = promotionConsumerService;
        this.sportDepartmentConsumerService = sportDepartmentConsumerService;
    }

    @PostConstruct
    public void createTopicElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createExchange(rabbitAdmin);
        createQueues(rabbitAdmin);
        doBinding(rabbitAdmin);
    }

    @Bean
    public MessageListenerContainer productionRegisterListener() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(PRODUCT_REGISTER_QUEUE.getValue());
        container.setMessageListener(productRegisterConsumerService);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer promotionListener() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(PROMOTION_QUEUE.getValue());
        container.setMessageListener(promotionConsumerService);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer sportDepartmentListener() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(SPORT_DEPARTMENT_QUEUE.getValue());
        container.setMessageListener(sportDepartmentConsumerService);
        container.start();
        return container;
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
