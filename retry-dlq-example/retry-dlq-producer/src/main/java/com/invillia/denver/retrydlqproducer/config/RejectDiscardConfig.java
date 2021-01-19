package com.invillia.denver.retrydlqproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RejectDiscardConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void createDirectElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createRejectDiscardQueue(rabbitAdmin);
        createBindingRejectDiscardQueue(rabbitAdmin);
    }

    private void createBindingRejectDiscardQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(QueueConstants.REJECT_DISCARD_QUEUE,
                Binding.DestinationType.QUEUE,
                QueueConstants.RETRY_DIRECT_EXCHANGE,
                QueueConstants.BINDING_REJECT_QUEUE,
                null));
    }

    private void createRejectDiscardQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(QueueConstants.REJECT_DISCARD_QUEUE)
                .build());
    }
}
