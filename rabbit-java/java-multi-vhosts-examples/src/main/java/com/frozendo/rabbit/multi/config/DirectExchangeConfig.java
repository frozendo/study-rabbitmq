package com.frozendo.rabbit.multi.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.SMALL_QUANTITY_KEY;
import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.BIG_QUANTITY_KEY;
import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.SMALL_QUANTITY_QUEUE;
import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.JAVA_DIRECT_PRODUCT_EX;
import static com.frozendo.rabbit.multi.domain.enums.DirectEnum.BIG_QUANTITY_QUEUE;

@Component
public class DirectExchangeConfig {

    private static Connection connection;
    private static Channel channel;

    public DirectExchangeConfig() {
        try {
            startConnection();
            config();
        } catch (IOException | TimeoutException ex) {
            System.out.println("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General exception");
            ex.printStackTrace();
        }
    }

    public static Channel getChannel() {
        return channel;
    }

    private void startConnection() throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setUsername("user-direct");
        factory.setPassword("test12");
        factory.setVirtualHost("/vdirect");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    private void config() throws IOException {
        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(JAVA_DIRECT_PRODUCT_EX.getValue(), "direct", true);
    }

    private void createQueues(Channel channel) throws IOException {
        channel.queueDeclare(BIG_QUANTITY_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(SMALL_QUANTITY_QUEUE.getValue(), true, false, false, null);
    }

    private void doBinding(Channel channel) throws IOException {
        channel.queueBind(BIG_QUANTITY_QUEUE.getValue(), JAVA_DIRECT_PRODUCT_EX.getValue(), BIG_QUANTITY_KEY.getValue());
        channel.queueBind(SMALL_QUANTITY_QUEUE.getValue(), JAVA_DIRECT_PRODUCT_EX.getValue(), SMALL_QUANTITY_KEY.getValue());
    }

}
