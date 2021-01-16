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

    public static final String SAMPLE_FANOUT_EXCHANGE = "sample-fanout-exchange";

    public static final String SAMPLE_FANOUT_QUEUE = "sample-fanout-queue";
    public static final String OTHER_SAMPLE_FANOUT_QUEUE = "other-sample-fanout-queue";

    @Bean
    Exchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange(SAMPLE_FANOUT_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingSampleQueueFanout() {
        return BindingBuilder
                .bind(sampleFanoutQueue()).to(fanoutExchange())
                .with("")
                .noargs();
    }

    @Bean
    Binding bindingOtherSampleQueueFanout() {
        return BindingBuilder
                .bind(otherSampleFanoutQueue())
                .to(fanoutExchange())
                .with("")
                .noargs();
    }

    @Bean
    Queue sampleFanoutQueue() {
        return QueueBuilder
                .durable(SAMPLE_FANOUT_QUEUE)
                .build();
    }

    @Bean
    Queue otherSampleFanoutQueue() {
        return QueueBuilder
                .durable(OTHER_SAMPLE_FANOUT_QUEUE)
                .build();
    }

}
