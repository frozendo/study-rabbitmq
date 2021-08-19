package com.frozendo.rabbit.sample.config;

import com.rabbitmq.client.Channel;

import java.io.IOException;

import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.SMALL_QUANTITY_KEY;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.BIG_QUANTITY_KEY;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.SMALL_QUANTITY_QUEUE;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.JAVA_DIRECT_PRODUCT_EX;
import static com.frozendo.rabbit.sample.domain.enums.DirectEnum.BIG_QUANTITY_QUEUE;

public class DirectExchangeConfig {

    public static void config(Channel channel) throws IOException {
        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private static void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(JAVA_DIRECT_PRODUCT_EX.getValue(), "direct", true);
    }

    private static void createQueues(Channel channel) throws IOException {
        channel.queueDeclare(BIG_QUANTITY_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(SMALL_QUANTITY_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        channel.queueBind(BIG_QUANTITY_QUEUE.getValue(), JAVA_DIRECT_PRODUCT_EX.getValue(), BIG_QUANTITY_KEY.getValue());
        channel.queueBind(SMALL_QUANTITY_QUEUE.getValue(), JAVA_DIRECT_PRODUCT_EX.getValue(), SMALL_QUANTITY_KEY.getValue());
    }

}
