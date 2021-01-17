package com.invillia.denver.rabbitadminproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TopicConfig {

    public static final String ADMIN_LISTEN_TOPIC_EXCHANGE = "admin-listen-topic-exchange";

    public static final String ADMIN_LISTEN_TOPIC_QUEUE = "admin-listen-topic-queue";
    public static final String OTHER_ADMIN_LISTEN_TOPIC_QUEUE = "other-admin-listen-topic-queue";
    public static final String THIRD_ADMIN_LISTEN_TOPIC_QUEUE = "third-admin-listen-topic-queue";

    public static final String BINDING_EXAMPLE = "example.#";
    public static final String BINDING_OTHER_EXAMPLE = "example.other.#";
    public static final String BINDING_THIRD = "example.*.test";

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    private void createTopicElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createTopicExchange(rabbitAdmin);
        createAdminListenTopicQueue(rabbitAdmin);
        createOtherAdminListenTopicQueue(rabbitAdmin);
        createThirdAdminListenTopicQueue(rabbitAdmin);
        createBindingAdminListenQueueTopic(rabbitAdmin);
        createBindingOtherAdminListenQueueTopic(rabbitAdmin);
        createBindingThirdAdminListenQueueTopic(rabbitAdmin);
    }

    private void createTopicExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .topicExchange(ADMIN_LISTEN_TOPIC_EXCHANGE)
                .build());
    }

    private void createBindingAdminListenQueueTopic(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(ADMIN_LISTEN_TOPIC_QUEUE,
                Binding.DestinationType.QUEUE,
                ADMIN_LISTEN_TOPIC_EXCHANGE,
                BINDING_EXAMPLE,
                null));
    }

    private void createBindingOtherAdminListenQueueTopic(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(OTHER_ADMIN_LISTEN_TOPIC_QUEUE,
                Binding.DestinationType.QUEUE,
                ADMIN_LISTEN_TOPIC_EXCHANGE,
                BINDING_OTHER_EXAMPLE,
                null));
    }

    private void createBindingThirdAdminListenQueueTopic(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(THIRD_ADMIN_LISTEN_TOPIC_QUEUE,
                Binding.DestinationType.QUEUE,
                ADMIN_LISTEN_TOPIC_EXCHANGE,
                BINDING_THIRD,
                null));
    }

    private void createAdminListenTopicQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(ADMIN_LISTEN_TOPIC_QUEUE)
                .build());
    }

    private void createOtherAdminListenTopicQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(OTHER_ADMIN_LISTEN_TOPIC_QUEUE)
                .build());
    }

    private void createThirdAdminListenTopicQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(THIRD_ADMIN_LISTEN_TOPIC_QUEUE)
                .build());
    }
}
