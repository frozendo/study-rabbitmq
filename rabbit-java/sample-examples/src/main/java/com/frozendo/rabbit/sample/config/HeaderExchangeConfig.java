package com.frozendo.rabbit.sample.config;

import com.frozendo.rabbit.sample.domain.enums.HeaderEnum;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

public class HeaderExchangeConfig {

    public static void config(Channel channel) throws IOException {
        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private static void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(HeaderEnum.JAVA_HEADER_PRODUCT_EX.getValue(), "headers", true);
    }

    private static void createQueues(Channel channel) throws IOException {
        channel.queueDeclare(HeaderEnum.BIG_GIFT_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(HeaderEnum.SMALL_GIFT_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        Map<String, Object> bigGiftHeaders = Map.of(HeaderEnum.QUANTITY_HEADER.getValue(), Boolean.TRUE.toString(),
                HeaderEnum.PRICE_HEADER.getValue(), Boolean.TRUE.toString(),
                HeaderEnum.X_MATCH_HEADER_KEY.getValue(), "all");

        Map<String, Object> smallGiftHeaders = Map.of(HeaderEnum.QUANTITY_HEADER.getValue(), Boolean.TRUE.toString(),
                HeaderEnum.PRICE_HEADER.getValue(), Boolean.TRUE.toString(),
                HeaderEnum.X_MATCH_HEADER_KEY.getValue(), "any");


        channel.queueBind(HeaderEnum.BIG_GIFT_QUEUE.getValue(), HeaderEnum.JAVA_HEADER_PRODUCT_EX.getValue(), StringUtils.EMPTY, bigGiftHeaders);
        channel.queueBind(HeaderEnum.SMALL_GIFT_QUEUE.getValue(), HeaderEnum.JAVA_HEADER_PRODUCT_EX.getValue(), StringUtils.EMPTY, smallGiftHeaders);
    }
}
