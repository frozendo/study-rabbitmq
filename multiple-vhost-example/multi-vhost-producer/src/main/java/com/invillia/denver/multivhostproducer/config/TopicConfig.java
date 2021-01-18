package com.invillia.denver.multivhostproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TopicConfig {

    public static final String MULTI_VHOST_TOPIC_EXCHANGE = "multi-vhost-topic-exchange";

    public static final String MULTI_VHOST_TOPIC_QUEUE = "multi-vhost-topic-queue";
    public static final String OTHER_MULTI_VHOST_TOPIC_QUEUE = "other-multi-vhost-topic-queue";
    public static final String THIRD_MULTI_VHOST_TOPIC_QUEUE = "third-multi-vhost-topic-queue";

    public static final String BINDING_EXAMPLE = "example.#";
    public static final String BINDING_OTHER_EXAMPLE = "example.other.#";
    public static final String BINDING_THIRD = "example.*.test";

    @Autowired
    @Qualifier("topicConnectionFactory")
    private ConnectionFactory connectionFactory;

    @PostConstruct
    private void createTopicElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createTopicExchange(rabbitAdmin);
        createMultiVhostTopicQueue(rabbitAdmin);
        createOtherMultiVhostTopicQueue(rabbitAdmin);
        createThirdMultiVhostTopicQueue(rabbitAdmin);
        createBindingMultiVhostQueueTopic(rabbitAdmin);
        createBindingOtherMultiVhostQueueTopic(rabbitAdmin);
        createBindingThirdMultiVhostQueueTopic(rabbitAdmin);
    }

    private void createTopicExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .topicExchange(MULTI_VHOST_TOPIC_EXCHANGE)
                .build());
    }

    private void createBindingMultiVhostQueueTopic(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(MULTI_VHOST_TOPIC_QUEUE,
                Binding.DestinationType.QUEUE,
                MULTI_VHOST_TOPIC_EXCHANGE,
                BINDING_EXAMPLE,
                null));
    }

    private void createBindingOtherMultiVhostQueueTopic(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(OTHER_MULTI_VHOST_TOPIC_QUEUE,
                Binding.DestinationType.QUEUE,
                MULTI_VHOST_TOPIC_EXCHANGE,
                BINDING_OTHER_EXAMPLE,
                null));
    }

    private void createBindingThirdMultiVhostQueueTopic(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(THIRD_MULTI_VHOST_TOPIC_QUEUE,
                Binding.DestinationType.QUEUE,
                MULTI_VHOST_TOPIC_EXCHANGE,
                BINDING_THIRD,
                null));
    }

    private void createMultiVhostTopicQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(MULTI_VHOST_TOPIC_QUEUE)
                .build());
    }

    private void createOtherMultiVhostTopicQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(OTHER_MULTI_VHOST_TOPIC_QUEUE)
                .build());
    }

    private void createThirdMultiVhostTopicQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(THIRD_MULTI_VHOST_TOPIC_QUEUE)
                .build());
    }
}
