package com.invillia.denver.retrydlqproducer.config;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AmqpRabbitConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void createDirectElements() {
        var rabbitAdmin = new RabbitAdmin(connectionFactory);
        createDirectExchange(rabbitAdmin);
        createDlxDirectExchange(rabbitAdmin);
    }

    private void createDirectExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .directExchange(QueueConstants.RETRY_DIRECT_EXCHANGE)
                .build());
    }

    private void createDlxDirectExchange(RabbitAdmin rabbitAdmin) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .directExchange(QueueConstants.RETRY_DLX_DIRECT_EXCHANGE)
                .build());
    }

}
