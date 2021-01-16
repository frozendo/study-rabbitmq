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

    public static final String SAMPLE_HEADER_EXCHANGE = "sample-header-exchange";

    public static final String SAMPLE_HEADER_QUEUE = "sample-header-queue";
    public static final String OTHER_SAMPLE_HEADER_QUEUE = "other-sample-header-queue";

    public static final String TEST_HEADER_KEY = "test";
    public static final String TYPE_HEADER_KEY = "type";
    public static final String X_MATCH_HEADER_KEY = "x-match";

    @Bean
    Exchange headerExchange() {
        return ExchangeBuilder
                .headersExchange(SAMPLE_HEADER_EXCHANGE)
                .build();
    }

    @Bean
    Binding bindingSampleQueueHeader() {
        Map<String, Object> map = Map.of(
                TEST_HEADER_KEY, "example-test",
                TYPE_HEADER_KEY, "all-header",
                X_MATCH_HEADER_KEY, "all"
        );

        return BindingBuilder
                .bind(sampleHeaderQueue()).to(headerExchange())
                .with("")
                .and(map);
    }

    @Bean
    Binding bindingOtherSampleQueueHeader() {
        Map<String, Object> map = Map.of(
                TEST_HEADER_KEY, "other-test",
                TYPE_HEADER_KEY, "any-header",
                X_MATCH_HEADER_KEY, "any"
        );

        return BindingBuilder
                .bind(otherSampleHeaderQueue()).to(headerExchange())
                .with("")
                .and(map);
    }

    @Bean
    Queue sampleHeaderQueue() {
        return QueueBuilder
                .durable(SAMPLE_HEADER_QUEUE)
                .build();
    }

    @Bean
    Queue otherSampleHeaderQueue() {
        return QueueBuilder
                .durable(OTHER_SAMPLE_HEADER_QUEUE)
                .build();
    }
}
