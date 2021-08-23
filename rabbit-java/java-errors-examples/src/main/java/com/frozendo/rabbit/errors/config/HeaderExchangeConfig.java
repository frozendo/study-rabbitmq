package com.frozendo.rabbit.errors.config;

import com.frozendo.rabbit.errors.domain.enums.DlqEnum;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.BIG_GIFT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.BIG_GIFT_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.BIG_GIFT_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.JAVA_ERRORS_BIG_GIFT_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.JAVA_ERRORS_SMALL_GIFT_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.JAVA_HEADER_PRODUCT_EX;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.PRICE_HEADER;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.QUANTITY_HEADER;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.SMALL_GIFT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.SMALL_GIFT_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.SMALL_GIFT_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.HeaderEnum.X_MATCH_HEADER_KEY;

public class HeaderExchangeConfig {

    public static void config(Channel channel) throws IOException {
        createDlqQueues(channel);
        doDlqBinding(channel);

        createExchange(channel);
        createQueues(channel);
        createDelayedQueues(channel);
        doBinding(channel);
    }

    private static void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(JAVA_HEADER_PRODUCT_EX.getValue(), "headers", true);
    }

    private static void createQueues(Channel channel) throws IOException {
        Map<String, Object> bigGiftArgs = new HashMap<>();
        bigGiftArgs.put("x-dead-letter-exchange", DlqEnum.JAVA_ERRORS_DLQ_EX.getValue());
        bigGiftArgs.put("x-dead-letter-routing-key", JAVA_ERRORS_BIG_GIFT_DLQ_KEY.getValue());
        channel.queueDeclare(BIG_GIFT_QUEUE.getValue(), true, false, false, bigGiftArgs);

        Map<String, Object> smallGiftArgs = new HashMap<>();
        smallGiftArgs.put("x-dead-letter-exchange", DlqEnum.JAVA_ERRORS_DLQ_EX.getValue());
        smallGiftArgs.put("x-dead-letter-routing-key", JAVA_ERRORS_SMALL_GIFT_DLQ_KEY.getValue());
        channel.queueDeclare(SMALL_GIFT_QUEUE.getValue(), true, false, false, smallGiftArgs);
    }

    private static void createDelayedQueues(Channel channel) throws IOException {
        Map<String, Object> bigGiftArgs = new HashMap<>();
        bigGiftArgs.put("x-dead-letter-exchange", StringUtils.EMPTY);
        bigGiftArgs.put("x-dead-letter-routing-key", BIG_GIFT_QUEUE.getValue());
        channel.queueDeclare(BIG_GIFT_DELAYED_QUEUE.getValue(), true, false, false, bigGiftArgs);

        Map<String, Object> smallGiftArgs = new HashMap<>();
        smallGiftArgs.put("x-dead-letter-exchange", StringUtils.EMPTY);
        smallGiftArgs.put("x-dead-letter-routing-key", SMALL_GIFT_QUEUE.getValue());
        channel.queueDeclare(SMALL_GIFT_DELAYED_QUEUE.getValue(), true, false, false, smallGiftArgs);
    }

    private static void createDlqQueues(Channel channel) throws IOException {
        channel.queueDeclare(BIG_GIFT_DLQ_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(SMALL_GIFT_DLQ_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        Map<String, Object> bigGiftHeaders = Map.of(QUANTITY_HEADER.getValue(), Boolean.TRUE.toString(),
                PRICE_HEADER.getValue(), Boolean.TRUE.toString(),
                X_MATCH_HEADER_KEY.getValue(), "all");

        Map<String, Object> smallGiftHeaders = Map.of(QUANTITY_HEADER.getValue(), Boolean.TRUE.toString(),
                PRICE_HEADER.getValue(), Boolean.TRUE.toString(),
                X_MATCH_HEADER_KEY.getValue(), "any");


        channel.queueBind(BIG_GIFT_QUEUE.getValue(), JAVA_HEADER_PRODUCT_EX.getValue(), StringUtils.EMPTY, bigGiftHeaders);
        channel.queueBind(SMALL_GIFT_QUEUE.getValue(), JAVA_HEADER_PRODUCT_EX.getValue(), StringUtils.EMPTY, smallGiftHeaders);
    }

    private static void doDlqBinding(Channel channel) throws IOException {
        channel.queueBind(BIG_GIFT_DLQ_QUEUE.getValue(), DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), JAVA_ERRORS_BIG_GIFT_DLQ_KEY.getValue());
        channel.queueBind(SMALL_GIFT_DLQ_QUEUE.getValue(), DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), JAVA_ERRORS_SMALL_GIFT_DLQ_KEY.getValue());
    }
}
