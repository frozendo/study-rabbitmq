package com.frozendo.rabbit.errors.config;

import com.frozendo.rabbit.errors.domain.enums.DlqEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.DEPARTMENT_BINDING_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.DEPARTMENT_PROMOTION_BINDING_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.DEPARTMENT_SPORT_BINDING_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.REGISTER_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.SPORT_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.SPRING_TOPIC_PRODUCT_EX;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PRODUCT_REGISTER_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PRODUCT_REGISTER_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PRODUCT_REGISTER_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.SPORT_DEPARTMENT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.SPORT_DEPARTMENT_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.SPORT_DEPARTMENT_QUEUE;

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
        createDelayedQueues(rabbitAdmin);
        createDlqQueues(rabbitAdmin);
        doBinding(rabbitAdmin);
        doDlqBinding(rabbitAdmin);
    }

    private void createExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .topicExchange(SPRING_TOPIC_PRODUCT_EX.getValue())
                .build());
        rabbitAdmin.declareExchange(ExchangeBuilder
                .directExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .build());
    }

    private void createQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PRODUCT_REGISTER_QUEUE.getValue())
                .deadLetterExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .deadLetterRoutingKey(REGISTER_DLQ_KEY.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SPORT_DEPARTMENT_QUEUE.getValue())
                .deadLetterExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .deadLetterRoutingKey(SPORT_DLQ_KEY.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PROMOTION_QUEUE.getValue())
                .deadLetterExchange(DlqEnum.SPRING_ERRORS_DLQ_EX.getValue())
                .deadLetterRoutingKey(PROMOTION_DLQ_KEY.getValue())
                .build());
    }

    private void createDelayedQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PRODUCT_REGISTER_DELAYED_QUEUE.getValue())
                .deadLetterExchange(StringUtils.EMPTY)
                .deadLetterRoutingKey(PRODUCT_REGISTER_QUEUE.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SPORT_DEPARTMENT_DELAYED_QUEUE.getValue())
                .deadLetterExchange(StringUtils.EMPTY)
                .deadLetterRoutingKey(SPORT_DEPARTMENT_QUEUE.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PROMOTION_DELAYED_QUEUE.getValue())
                .deadLetterExchange(StringUtils.EMPTY)
                .deadLetterRoutingKey(PROMOTION_QUEUE.getValue())
                .build());
    }

    private void createDlqQueues(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PRODUCT_REGISTER_DLQ_QUEUE.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SPORT_DEPARTMENT_DLQ_QUEUE.getValue())
                .build());

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(PROMOTION_DLQ_QUEUE.getValue())
                .build());
    }

    private void doBinding(RabbitAdmin rabbitAdmin) {
        var registerBinding = new Binding(PRODUCT_REGISTER_QUEUE.getValue(),
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

        rabbitAdmin.declareBinding(registerBinding);
        rabbitAdmin.declareBinding(sportDepartmentBinding);
        rabbitAdmin.declareBinding(promotionBinding);
    }

    private void doDlqBinding(RabbitAdmin rabbitAdmin) {
        var registerDlqBinding = new Binding(PRODUCT_REGISTER_DLQ_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                DlqEnum.SPRING_ERRORS_DLQ_EX.getValue(),
                REGISTER_DLQ_KEY.getValue(),
                null);

        var sportDepartmentDlqBinding = new Binding(SPORT_DEPARTMENT_DLQ_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                DlqEnum.SPRING_ERRORS_DLQ_EX.getValue(),
                SPORT_DLQ_KEY.getValue(),
                null);

        var promotionDlqBinding = new Binding(PROMOTION_DLQ_QUEUE.getValue(),
                Binding.DestinationType.QUEUE,
                DlqEnum.SPRING_ERRORS_DLQ_EX.getValue(),
                PROMOTION_DLQ_KEY.getValue(),
                null);

        rabbitAdmin.declareBinding(registerDlqBinding);
        rabbitAdmin.declareBinding(sportDepartmentDlqBinding);
        rabbitAdmin.declareBinding(promotionDlqBinding);
    }
}
