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
public class DirectConfig {

    public static final String SAMPLE_DIRECT_EXCHANGE = "sample-direct-exchange";

    public static final String SAMPLE_DIRECT_QUEUE = "sample-direct-queue";
    public static final String OTHER_SAMPLE_DIRECT_QUEUE = "other-sample-direct-queue";

    public static final String EXAMPLE_QUEUE_BINDING = "example.queue.binding";
    public static final String OTHER_QUEUE_BINDING = "other.queue.binding";

    @Bean
    Exchange directExchange() {
        return ExchangeBuilder
                .directExchange(SAMPLE_DIRECT_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingSampleQueueDirect() {
        return BindingBuilder
                .bind(sampleDirectQueue()).to(directExchange())
                .with(EXAMPLE_QUEUE_BINDING)
                .noargs();
    }

    @Bean
    Binding bindingOtherSampleQueueDirect() {
        return BindingBuilder
                .bind(otherSampleDirectQueue())
                .to(directExchange())
                .with(OTHER_QUEUE_BINDING)
                .noargs();
    }

    @Bean
    Queue sampleDirectQueue() {
        return QueueBuilder
                .durable(SAMPLE_DIRECT_QUEUE)
                .build();
    }

    @Bean
    Queue otherSampleDirectQueue() {
        return QueueBuilder
                .durable(OTHER_SAMPLE_DIRECT_QUEUE)
                .build();
    }

}
