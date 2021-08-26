package com.frozendo.rabbit.multi.config;

import com.frozendo.rabbit.multi.domain.enums.FanoutEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class FanoutExchangeConfig {

    private static final Logger log = LoggerFactory.getLogger(FanoutExchangeConfig.class);

    private static Channel channel;

    public FanoutExchangeConfig() {
        try {
            startConnection();
            config();
        } catch (IOException | TimeoutException ex) {
            log.error("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            log.error("General exception");
            ex.printStackTrace();
        }
    }

    public static Channel getChannel() {
        try {
            if (channel == null) {
                startConnection();
            }
            return channel;
        } catch (IOException | TimeoutException ex) {
            log.error("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            log.error("General exception");
            ex.printStackTrace();
        }
        return null;
    }

    private static void startConnection() throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setUsername("user-fanout");
        factory.setPassword("test12");
        factory.setVirtualHost("/vfanout");
        var connection = factory.newConnection();
        channel = connection.createChannel();
    }

    private void config() throws IOException {
        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(FanoutEnum.JAVA_FANOUT_PRODUCT_EX.getValue(), "fanout", true);
    }

    private void createQueues(Channel channel) throws IOException {
        channel.queueDeclare(FanoutEnum.INVENTORY_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(FanoutEnum.PAYMENT_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        channel.queueBind(FanoutEnum.INVENTORY_QUEUE.getValue(), FanoutEnum.JAVA_FANOUT_PRODUCT_EX.getValue(), StringUtils.EMPTY);
        channel.queueBind(FanoutEnum.PAYMENT_QUEUE.getValue(), FanoutEnum.JAVA_FANOUT_PRODUCT_EX.getValue(), StringUtils.EMPTY);
    }
}
