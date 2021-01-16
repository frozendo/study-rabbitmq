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

    public static final String SAMPLE_TOPIC_EXCHANGE = "sample-topic-exchange";

    public static final String SAMPLE_TOPIC_QUEUE = "sample-topic-queue";
    public static final String OTHER_SAMPLE_TOPIC_QUEUE = "other-sample-topic-queue";
    public static final String THIRD_SAMPLE_TOPIC_QUEUE = "third-sample-topic-queue";

    public static final String BINDING_EXAMPLE = "example.#";
    public static final String BINDING_OTHER_EXAMPLE = "example.other.#";
    public static final String BINDING_THIRD = "example.*.test";

    @Bean
    Exchange topicExchange() {
        return ExchangeBuilder
                .topicExchange(SAMPLE_TOPIC_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingSampleQueueTopic() {
        return BindingBuilder
                .bind(sampleTopicQueue()).to(topicExchange())
                .with(BINDING_EXAMPLE)
                .noargs();
    }

    @Bean
    Binding bindingOtherSampleQueueTopic() {
        return BindingBuilder
                .bind(otherSampleTopicQueue())
                .to(topicExchange())
                .with(BINDING_OTHER_EXAMPLE)
                .noargs();
    }

    @Bean
    Binding bindingThirdSampleQueueTopic() {
        return BindingBuilder
                .bind(thirdSampleTopicQueue())
                .to(topicExchange())
                .with(BINDING_THIRD)
                .noargs();
    }

    @Bean
    Queue sampleTopicQueue() {
        return QueueBuilder
                .durable(SAMPLE_TOPIC_QUEUE)
                .build();
    }

    @Bean
    Queue otherSampleTopicQueue() {
        return QueueBuilder
                .durable(OTHER_SAMPLE_TOPIC_QUEUE)
                .build();
    }

    @Bean
    Queue thirdSampleTopicQueue() {
        return QueueBuilder
                .durable(THIRD_SAMPLE_TOPIC_QUEUE)
                .build();
    }
}
