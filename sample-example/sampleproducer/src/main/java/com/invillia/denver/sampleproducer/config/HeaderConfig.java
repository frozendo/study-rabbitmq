package com.invillia.denver.sampleproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HeaderConfig {

    public static final String HEADER_EXAMPLE_EXCHANGE = "header-example-exchange";

    public static final String EXAMPLE_HEADER_QUEUE = "example-header-queue";
    public static final String OTHER_EXAMPLE_HEADER_QUEUE = "other-example-header-queue";

    public static final String TEST_HEADER_KEY = "test";
    public static final String TYPE_HEADER_KEY = "type";
    public static final String X_MATCH_HEADER_KEY = "x-match";

    @Bean
    Exchange headerExchange() {
        return ExchangeBuilder
                .headersExchange(HEADER_EXAMPLE_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingExampleQueueHeader() {
        Map<String, Object> map = Map.of(
                TEST_HEADER_KEY, "example-test",
                TYPE_HEADER_KEY, "all-header",
                X_MATCH_HEADER_KEY, "all"
        );

        return BindingBuilder
                .bind(exampleHeaderQueue()).to(headerExchange())
                .with("")
                .and(map);
    }

    @Bean
    Binding bindingOtherExampleQueueHeader() {
        Map<String, Object> map = Map.of(
                TEST_HEADER_KEY, "other-test",
                TYPE_HEADER_KEY, "any-header",
                X_MATCH_HEADER_KEY, "any"
        );

        return BindingBuilder
                .bind(otherExampleHeaderQueue()).to(headerExchange())
                .with("")
                .and(map);
    }

    @Bean
    Queue exampleHeaderQueue() {
        return QueueBuilder
                .durable(EXAMPLE_HEADER_QUEUE)
                .build();
    }

    @Bean
    Queue otherExampleHeaderQueue() {
        return QueueBuilder
                .durable(OTHER_EXAMPLE_HEADER_QUEUE)
                .build();
    }
}
