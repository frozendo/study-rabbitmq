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

    public static final String SAMPLE_BEAN_FANOUT_EXCHANGE = "sample-bean-fanout-exchange";

    public static final String SAMPLE_BEAN_FANOUT_QUEUE = "sample-bean-fanout-queue";
    public static final String OTHER_SAMPLE_BEAN_FANOUT_QUEUE = "other-sample-bean-fanout-queue";

    @Bean
    Exchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange(SAMPLE_BEAN_FANOUT_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingSampleBeanQueueFanout() {
        return BindingBuilder
                .bind(sampleBeanQueueFanout())
                .to(fanoutExchange())
                .with("")
                .noargs();
    }

    @Bean
    Binding bindingOtherSampleBeanQueueFanout() {
        return BindingBuilder
                .bind(otherSampleBeanQueueFanout())
                .to(fanoutExchange())
                .with("")
                .noargs();
    }

    @Bean
    Queue sampleBeanQueueFanout() {
        return QueueBuilder
                .durable(SAMPLE_BEAN_FANOUT_QUEUE)
                .build();
    }

    @Bean
    Queue otherSampleBeanQueueFanout() {
        return QueueBuilder
                .durable(OTHER_SAMPLE_BEAN_FANOUT_QUEUE)
                .build();
    }

}
