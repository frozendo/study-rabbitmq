package com.invillia.denver.retrydlqproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
public class RejectSaveConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void createDirectElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createRejectSaveQueue(rabbitAdmin);
        createRejectSaveDlq(rabbitAdmin);
        createBindingRejectSaveQueue(rabbitAdmin);
        createBindingRejectSaveDlq(rabbitAdmin);
    }

    private void createBindingRejectSaveQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(QueueConstants.REJECT_SAVE_QUEUE,
                Binding.DestinationType.QUEUE,
                QueueConstants.RETRY_DIRECT_EXCHANGE,
                QueueConstants.BINDING_REJECT_SAVE_QUEUE,
                null));
    }

    private void createBindingRejectSaveDlq(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(QueueConstants.REJECT_SAVE_DLQ,
                Binding.DestinationType.QUEUE,
                QueueConstants.RETRY_DLX_DIRECT_EXCHANGE,
                QueueConstants.BINDING_REJECT_SAVE_DLQ,
                null));
    }

    private void createRejectSaveQueue(RabbitAdmin rabbitAdmin) {
        Map<String, Object> args = Map.of(
                QueueConstants.X_DEAD_LETTER_EXCHANGE, QueueConstants.RETRY_DLX_DIRECT_EXCHANGE,
                QueueConstants.X_DEAD_LETTER_ROUTING_KEY, QueueConstants.BINDING_REJECT_SAVE_DLQ
        );

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(QueueConstants.REJECT_SAVE_QUEUE)
                .withArguments(args)
                .build());
    }

    private void createRejectSaveDlq(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(QueueConstants.REJECT_SAVE_DLQ)
                .build());
    }
}
