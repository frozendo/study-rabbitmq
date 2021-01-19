package com.invillia.denver.retrydlqproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class LoopExampleConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void createDirectElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createLoopExampleQueue(rabbitAdmin);
        createBindingLoopExampleQueue(rabbitAdmin);
    }

    private void createBindingLoopExampleQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(QueueConstants.LOOP_EXAMPLE_QUEUE,
                Binding.DestinationType.QUEUE,
                QueueConstants.RETRY_DIRECT_EXCHANGE,
                QueueConstants.BINDING_LOOP_QUEUE,
                null));
    }

    private void createLoopExampleQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(QueueConstants.LOOP_EXAMPLE_QUEUE)
                .build());
    }

}
