package com.invillia.denver.multivhostconsumer.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.topic-host}")
    private String topicHost;

    @Value("${spring.rabbitmq.topic-port}")
    private int topicPort;

    @Value("${spring.rabbitmq.topic-username}")
    private String topicUsername;

    @Value("${spring.rabbitmq.topic-password}")
    private String topicPassword;

    @Value("${spring.rabbitmq.topic-virtual-host}")
    private String topicVirtualHost;

    @Bean
    @Primary
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> primaryListenerContainer() {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(primaryConnectionFactory());
        return factory;
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> topicListenerContainer() {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(topicConnectionFactory());
        return factory;
    }

    @Bean
    @Primary
    public ConnectionFactory primaryConnectionFactory() {
        var cacheConnectionFactory = new CachingConnectionFactory();
        cacheConnectionFactory.setHost(host);
        cacheConnectionFactory.setPort(port);
        cacheConnectionFactory.setUsername(username);
        cacheConnectionFactory.setPassword(password);
        cacheConnectionFactory.setVirtualHost(virtualHost);
        return cacheConnectionFactory;
    }

    @Bean
    public ConnectionFactory topicConnectionFactory() {
        var cacheConnectionFactory = new CachingConnectionFactory();
        cacheConnectionFactory.setHost(topicHost);
        cacheConnectionFactory.setPort(topicPort);
        cacheConnectionFactory.setUsername(topicUsername);
        cacheConnectionFactory.setPassword(topicPassword);
        cacheConnectionFactory.setVirtualHost(topicVirtualHost);
        return cacheConnectionFactory;
    }
}
