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

    public static final String SAMPLE_BEAN_TOPIC_EXCHANGE = "sample-bean-topic-exchange";

    public static final String SAMPLE_BEAN_TOPIC_QUEUE = "sample-bean-topic-queue";
    public static final String OTHER_SAMPLE_BEAN_TOPIC_QUEUE = "other-sample-bean-topic-queue";
    public static final String THIRD_SAMPLE_BEAN_TOPIC_QUEUE = "third-sample-bean-topic-queue";

    public static final String BINDING_EXAMPLE = "example.#";
    public static final String BINDING_OTHER_EXAMPLE = "example.other.#";
    public static final String BINDING_THIRD = "example.*.test";

    @Bean
    Exchange topicExchange() {
        return ExchangeBuilder
                .topicExchange(SAMPLE_BEAN_TOPIC_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingSampleBeanQueueTopic() {
        return BindingBuilder
                .bind(sampleBeanQueueTopic()).to(topicExchange())
                .with(BINDING_EXAMPLE)
                .noargs();
    }

    @Bean
    Binding bindingOtherSampleBeanQueueTopic() {
        return BindingBuilder
                .bind(otherSampleBeanQueueTopic())
                .to(topicExchange())
                .with(BINDING_OTHER_EXAMPLE)
                .noargs();
    }

    @Bean
    Binding bindingThirdSampleBeanQueueTopic() {
        return BindingBuilder
                .bind(thirdSampleBeanQueueTopic())
                .to(topicExchange())
                .with(BINDING_THIRD)
                .noargs();
    }

    @Bean
    Queue sampleBeanQueueTopic() {
        return QueueBuilder
                .durable(SAMPLE_BEAN_TOPIC_QUEUE)
                .build();
    }

    @Bean
    Queue otherSampleBeanQueueTopic() {
        return QueueBuilder
                .durable(OTHER_SAMPLE_BEAN_TOPIC_QUEUE)
                .build();
    }

    @Bean
    Queue thirdSampleBeanQueueTopic() {
        return QueueBuilder
                .durable(THIRD_SAMPLE_BEAN_TOPIC_QUEUE)
                .build();
    }
}
