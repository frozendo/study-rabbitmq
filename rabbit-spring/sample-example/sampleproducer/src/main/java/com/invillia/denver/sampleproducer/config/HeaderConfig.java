package com.invillia.denver.sampleproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
public class HeaderConfig {

    public static final String SAMPLE_MANUAL_HEADER_EXCHANGE = "sample-manual-header-exchange";

    public static final String SAMPLE_MANUAL_HEADER_QUEUE = "sample-manual-header-queue";
    public static final String OTHER_SAMPLE_MANUAL_HEADER_QUEUE = "other-sample-manual-header-queue";

    public static final String TEST_HEADER_KEY = "test";
    public static final String TYPE_HEADER_KEY = "type";
    public static final String X_MATCH_HEADER_KEY = "x-match";

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    private void createHeaderElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createHeaderExchange(rabbitAdmin);
        createSampleManualQueue(rabbitAdmin);
        createOtherSampleManualQueue(rabbitAdmin);
        createBindingSampleManualQueue(rabbitAdmin);
        createBindingOtherSampleManualQueue(rabbitAdmin);
    }

    private void createHeaderExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .headersExchange(SAMPLE_MANUAL_HEADER_EXCHANGE)
                .build());
    }

    private void createBindingSampleManualQueue(RabbitAdmin rabbitAdmin) {
        Map<String, Object> map = Map.of(
                TEST_HEADER_KEY, "example-test",
                TYPE_HEADER_KEY, "all-header",
                X_MATCH_HEADER_KEY, "all"
        );
        rabbitAdmin.declareBinding(new Binding(SAMPLE_MANUAL_HEADER_QUEUE,
                Binding.DestinationType.QUEUE,
                SAMPLE_MANUAL_HEADER_EXCHANGE,
                "",
                map));
    }

    private void createBindingOtherSampleManualQueue(RabbitAdmin rabbitAdmin) {
        Map<String, Object> map = Map.of(
                TEST_HEADER_KEY, "other-test",
                TYPE_HEADER_KEY, "any-header",
                X_MATCH_HEADER_KEY, "any"
        );
        rabbitAdmin.declareBinding(new Binding(OTHER_SAMPLE_MANUAL_HEADER_QUEUE,
                Binding.DestinationType.QUEUE,
                SAMPLE_MANUAL_HEADER_EXCHANGE,
                "",
                map));
    }

    private void createSampleManualQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(SAMPLE_MANUAL_HEADER_QUEUE)
                .build());
    }

    private void createOtherSampleManualQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(OTHER_SAMPLE_MANUAL_HEADER_QUEUE)
                .build());
    }
}
