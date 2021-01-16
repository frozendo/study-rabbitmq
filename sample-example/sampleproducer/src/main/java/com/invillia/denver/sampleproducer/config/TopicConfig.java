package com.invillia.denver.sampleproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    public static final String TOPIC_EXAMPLE_EXCHANGE = "topic-example-exchange";

    public static final String EXAMPLE_TOPIC_QUEUE = "example-topic-queue";
    public static final String OTHER_EXAMPLE_TOPIC_QUEUE = "other-example-topic-queue";
    public static final String THIRD_EXAMPLE_TOPIC_QUEUE = "third-example-topic-queue";

    public static final String BINDING_EXAMPLE = "example.#";
    public static final String BINDING_OTHER_EXAMPLE = "example.other.#";
    public static final String BINDING_THIRD = "example.*.test";

    @Bean
    Exchange topicExchange() {
        return ExchangeBuilder
                .topicExchange(TOPIC_EXAMPLE_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingExampleQueueTopic() {
        return BindingBuilder
                .bind(exampleTopicQueue()).to(topicExchange())
                .with(BINDING_EXAMPLE)
                .noargs();
    }

    @Bean
    Binding bindingOtherExampleQueueTopic() {
        return BindingBuilder
                .bind(otherExampleTopicQueue())
                .to(topicExchange())
                .with(BINDING_OTHER_EXAMPLE)
                .noargs();
    }

    @Bean
    Binding bindingThirdExampleQueueTopic() {
        return BindingBuilder
                .bind(thirdExampleQueue())
                .to(topicExchange())
                .with(BINDING_THIRD)
                .noargs();
    }

    @Bean
    Queue exampleTopicQueue() {
        return QueueBuilder
                .durable(EXAMPLE_TOPIC_QUEUE)
                .build();
    }

    @Bean
    Queue otherExampleTopicQueue() {
        return QueueBuilder
                .durable(OTHER_EXAMPLE_TOPIC_QUEUE)
                .build();
    }

    @Bean
    Queue thirdExampleQueue() {
        return QueueBuilder
                .durable(THIRD_EXAMPLE_TOPIC_QUEUE)
                .build();
    }
}
