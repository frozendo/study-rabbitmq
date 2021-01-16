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
public class FanoutConfig {

    public static final String FANOUT_EXAMPLE_EXCHANGE = "fanout-example-exchange";

    public static final String EXAMPLE_FANOUT_QUEUE = "example-fanout-queue";
    public static final String OTHER_EXAMPLE_FANOUT_QUEUE = "other-example-fanout-queue";

    @Bean
    Exchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange(FANOUT_EXAMPLE_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingExampleQueueFanout() {
        return BindingBuilder
                .bind(exampleFanoutQueue()).to(fanoutExchange())
                .with("")
                .noargs();
    }

    @Bean
    Binding bindingOtherExampleQueueFanout() {
        return BindingBuilder
                .bind(otherExampleFanoutQueue())
                .to(fanoutExchange())
                .with("")
                .noargs();
    }

    @Bean
    Queue exampleFanoutQueue() {
        return QueueBuilder
                .durable(EXAMPLE_FANOUT_QUEUE)
                .build();
    }

    @Bean
    Queue otherExampleFanoutQueue() {
        return QueueBuilder
                .durable(OTHER_EXAMPLE_FANOUT_QUEUE)
                .build();
    }

}
