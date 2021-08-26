package com.frozendo.rabbit.multi.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitConfig {

    public static final String HOST = "localhost";
    public static final String PASSWORD = "test12";
    public static final int PORT = 5672;

    @Bean
    @Primary
    public ConnectionFactory defalutConnectinFactory() {
        var cacheConnectionFactory = new CachingConnectionFactory();
        cacheConnectionFactory.setHost(HOST);
        cacheConnectionFactory.setPort(PORT);
        cacheConnectionFactory.setUsername("admin");
        cacheConnectionFactory.setPassword(PASSWORD);
        cacheConnectionFactory.setVirtualHost("/");
        return cacheConnectionFactory;
    }

    @Bean
    public ConnectionFactory fanoutConnectionFactory() {
        var cacheConnectionFactory = new CachingConnectionFactory();
        cacheConnectionFactory.setHost(HOST);
        cacheConnectionFactory.setPort(PORT);
        cacheConnectionFactory.setUsername("user-fanout");
        cacheConnectionFactory.setPassword(PASSWORD);
        cacheConnectionFactory.setVirtualHost("/vfanout");
        return cacheConnectionFactory;
    }

    @Bean
    public ConnectionFactory topicConnectionFactory() {
        var cacheConnectionFactory = new CachingConnectionFactory();
        cacheConnectionFactory.setHost(HOST);
        cacheConnectionFactory.setPort(PORT);
        cacheConnectionFactory.setUsername("user-topic");
        cacheConnectionFactory.setPassword(PASSWORD);
        cacheConnectionFactory.setVirtualHost("/vtopic");
        return cacheConnectionFactory;
    }

    @Bean
    public ConnectionFactory directConnectionFactory() {
        var cacheConnectionFactory = new CachingConnectionFactory();
        cacheConnectionFactory.setHost(HOST);
        cacheConnectionFactory.setPort(PORT);
        cacheConnectionFactory.setUsername("user-direct");
        cacheConnectionFactory.setPassword(PASSWORD);
        cacheConnectionFactory.setVirtualHost("/vdirect");
        return cacheConnectionFactory;
    }

    @Bean
    public ConnectionFactory headerConnectionFactory() {
        var cacheConnectionFactory = new CachingConnectionFactory();
        cacheConnectionFactory.setHost(HOST);
        cacheConnectionFactory.setPort(PORT);
        cacheConnectionFactory.setUsername("user-header");
        cacheConnectionFactory.setPassword(PASSWORD);
        cacheConnectionFactory.setVirtualHost("/vheader");
        return cacheConnectionFactory;
    }

    @Bean
    public RabbitTemplate fanoutRabbitTemplate() {
        return new RabbitTemplate(fanoutConnectionFactory());
    }

    @Bean
    public RabbitTemplate topicRabbitTemplate() {
        return new RabbitTemplate(topicConnectionFactory());
    }

    @Bean
    @Primary
    public RabbitTemplate directRabbitTemplate() {
        return new RabbitTemplate(directConnectionFactory());
    }

    @Bean
    public RabbitTemplate headerRabbitTemplate() {
        return new RabbitTemplate(headerConnectionFactory());
    }
}
