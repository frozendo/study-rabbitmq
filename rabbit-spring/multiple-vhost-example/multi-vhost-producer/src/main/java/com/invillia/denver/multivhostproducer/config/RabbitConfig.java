package com.invillia.denver.multivhostproducer.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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

    @Bean
    @Primary
    public RabbitTemplate primaryRabbitTemplate() {
        var rabbitTemplate = new RabbitTemplate(primaryConnectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate topicRabbitTemplate() {
        var rabbitTemplate = new RabbitTemplate(topicConnectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}
