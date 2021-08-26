package com.frozendo.rabbit.sample.config;

import com.frozendo.rabbit.sample.domain.enums.FanoutEnum;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class FanoutExchangeConfig {

    private FanoutExchangeConfig() {}

    public static void config(Channel channel) throws IOException {
        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private static void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(FanoutEnum.JAVA_FANOUT_PRODUCT_EX.getValue(), "fanout", true);
    }

    private static void createQueues(Channel channel) throws IOException {
        channel.queueDeclare(FanoutEnum.INVENTORY_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(FanoutEnum.PAYMENT_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        channel.queueBind(FanoutEnum.INVENTORY_QUEUE.getValue(), FanoutEnum.JAVA_FANOUT_PRODUCT_EX.getValue(), StringUtils.EMPTY);
        channel.queueBind(FanoutEnum.PAYMENT_QUEUE.getValue(), FanoutEnum.JAVA_FANOUT_PRODUCT_EX.getValue(), StringUtils.EMPTY);
    }
}
