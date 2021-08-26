package com.frozendo.rabbit.sample.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitConfig {

    @Bean
    @Primary
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> listenerContainer() {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        var cacheConnectionFactory = new CachingConnectionFactory();
        cacheConnectionFactory.setHost("localhost");
        cacheConnectionFactory.setPort(5672);
        cacheConnectionFactory.setUsername("admin");
        cacheConnectionFactory.setPassword("test12");
        cacheConnectionFactory.setVirtualHost("/");
        return cacheConnectionFactory;
    }
}
