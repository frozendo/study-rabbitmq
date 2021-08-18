package com.frozendo.learnrabbit.config;

import com.rabbitmq.client.Channel;

import java.io.IOException;

import static com.frozendo.learnrabbit.domain.DirectEnum.EVEN_VALUE_KEY;
import static com.frozendo.learnrabbit.domain.DirectEnum.ODD_VALUE_KEY;
import static com.frozendo.learnrabbit.domain.DirectEnum.EVEN_QUANTITY_QUEUE;
import static com.frozendo.learnrabbit.domain.DirectEnum.JAVA_DIRECT_PRODUCT_EX;
import static com.frozendo.learnrabbit.domain.DirectEnum.ODD_QUANTITY_QUEUE;

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
        channel.queueDeclare(ODD_QUANTITY_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(EVEN_QUANTITY_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        channel.queueBind(ODD_QUANTITY_QUEUE.getValue(), JAVA_DIRECT_PRODUCT_EX.getValue(), ODD_VALUE_KEY.getValue());
        channel.queueBind(EVEN_QUANTITY_QUEUE.getValue(), JAVA_DIRECT_PRODUCT_EX.getValue(), EVEN_VALUE_KEY.getValue());
    }

}
