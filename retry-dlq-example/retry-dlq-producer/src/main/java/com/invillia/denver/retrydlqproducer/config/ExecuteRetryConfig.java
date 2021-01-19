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
public class ExecuteRetryConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void createDirectElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createExecuteRetryQueue(rabbitAdmin);
        createExecuteRetryDelayed(rabbitAdmin);
        createExecuteRetryDlq(rabbitAdmin);
        createBindingExecuteRetryQueue(rabbitAdmin);
        createBindingExecuteRetryDlq(rabbitAdmin);
    }

    private void createBindingExecuteRetryQueue(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(QueueConstants.EXECUTE_RETRY_QUEUE,
                Binding.DestinationType.QUEUE,
                QueueConstants.RETRY_DIRECT_EXCHANGE,
                QueueConstants.BINDING_EXECUTE_QUEUE,
                null));
    }

    private void createBindingExecuteRetryDlq(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareBinding(new Binding(QueueConstants.EXECUTE_RETRY_DLQ,
                Binding.DestinationType.QUEUE,
                QueueConstants.RETRY_DLX_DIRECT_EXCHANGE,
                QueueConstants.BINDING_EXECUTE_DLQ,
                null));
    }

    private void createExecuteRetryQueue(RabbitAdmin rabbitAdmin) {
        Map<String, Object> args = Map.of(
                QueueConstants.X_DEAD_LETTER_EXCHANGE, QueueConstants.RETRY_DLX_DIRECT_EXCHANGE,
                QueueConstants.X_DEAD_LETTER_ROUTING_KEY, QueueConstants.BINDING_EXECUTE_DLQ
        );

        rabbitAdmin.declareQueue(QueueBuilder
                .durable(QueueConstants.EXECUTE_RETRY_QUEUE)
                .withArguments(args)
                .build());
    }

    private void createExecuteRetryDelayed(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(QueueConstants.EXECUTE_RETRY_DELAYED)
                .build());
    }

    private void createExecuteRetryDlq(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(QueueConstants.EXECUTE_RETRY_DLQ)
                .build());
    }
}
