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

    public static final String DIRECT_EXAMPLE_EXCHANGE = "direct-example-exchange";

    public static final String EXAMPLE_DIRECT_QUEUE = "example-direct-queue";
    public static final String OTHER_EXAMPLE_DIRECT_QUEUE = "other-example-direct-queue";

    public static final String DIRECT_EXAMPLE_BINDING = "example.routing.binding";
    public static final String OTHER_EXAMPLE_BINDING = "other.routing.binding";

    @Bean
    Exchange directExchange() {
        return ExchangeBuilder
                .directExchange(DIRECT_EXAMPLE_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingExampleQueueDirect() {
        return BindingBuilder
                .bind(exampleDirectQueue()).to(directExchange())
                .with(DIRECT_EXAMPLE_BINDING)
                .noargs();
    }

    @Bean
    Binding bindingOtherExampleQueueDirect() {
        return BindingBuilder
                .bind(otherExampleDirectQueue())
                .to(directExchange())
                .with(OTHER_EXAMPLE_BINDING)
                .noargs();
    }

    @Bean
    Queue exampleDirectQueue() {
        return QueueBuilder
                .durable(EXAMPLE_DIRECT_QUEUE)
                .build();
    }

    @Bean
    Queue otherExampleDirectQueue() {
        return QueueBuilder
                .durable(OTHER_EXAMPLE_DIRECT_QUEUE)
                .build();
    }

}
