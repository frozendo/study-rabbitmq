package com.frozendo.rabbit.sample.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitBaseConfig {

    private static Connection connection;
    private static Channel channel;

    @PostConstruct
    public void init() {
        startConnection();
        configRabbit();
    }

    public Connection getConnection() {
        if (Objects.isNull(connection)) {
            startConnection();
        }
        return connection;
    }

    public Channel getChannel() {
        if (Objects.isNull(channel)) {
            startConnection();
        }
        return channel;
    }

    private void configRabbit() {
        try {
            DirectExchangeConfig.config(channel);
            TopicExchangeConfig.config(channel);
            FanoutExchangeConfig.config(channel);
            HeaderExchangeConfig.config(channel);
        } catch (IOException ex) {
            System.out.println("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General exception");
            ex.printStackTrace();
        }
    }

    private void startConnection() {
        var factory = new ConnectionFactory();
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException ex) {
            System.out.println("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General exception");
            ex.printStackTrace();
        }
    }

}
