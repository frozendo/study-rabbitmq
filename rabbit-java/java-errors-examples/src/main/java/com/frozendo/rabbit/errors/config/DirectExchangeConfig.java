package com.frozendo.rabbit.errors.config;

import com.frozendo.rabbit.errors.domain.enums.DlqEnum;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.BIG_QUANTITY_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.BIG_QUANTITY_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.JAVA_ERRORS_BIG_QUANTITY_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.JAVA_ERRORS_SMALL_QUANTITY_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.SMALL_QUANTITY_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.SMALL_QUANTITY_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.SMALL_QUANTITY_KEY;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.BIG_QUANTITY_KEY;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.SMALL_QUANTITY_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.JAVA_DIRECT_PRODUCT_EX;
import static com.frozendo.rabbit.errors.domain.enums.DirectEnum.BIG_QUANTITY_QUEUE;

public class DirectExchangeConfig {

    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    private DirectExchangeConfig() {}

    public static void config(Channel channel) throws IOException {
        createDlqQueues(channel);
        doDlqBinding(channel);

        createExchange(channel);
        createQueues(channel);
        createDelayedQueues(channel);
        doBinding(channel);
    }

    private static void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(JAVA_DIRECT_PRODUCT_EX.getValue(), "direct", true);
    }

    private static void createQueues(Channel channel) throws IOException {
        Map<String, Object> bigQuantityArgs = new HashMap<>();
        bigQuantityArgs.put(X_DEAD_LETTER_EXCHANGE, DlqEnum.JAVA_ERRORS_DLQ_EX.getValue());
        bigQuantityArgs.put(X_DEAD_LETTER_ROUTING_KEY, JAVA_ERRORS_BIG_QUANTITY_DLQ_KEY.getValue());
        channel.queueDeclare(BIG_QUANTITY_QUEUE.getValue(), true, false, false, bigQuantityArgs);

        Map<String, Object> smallQuantityArgs = new HashMap<>();
        smallQuantityArgs.put(X_DEAD_LETTER_EXCHANGE, DlqEnum.JAVA_ERRORS_DLQ_EX.getValue());
        smallQuantityArgs.put(X_DEAD_LETTER_ROUTING_KEY, JAVA_ERRORS_SMALL_QUANTITY_DLQ_KEY.getValue());
        channel.queueDeclare(SMALL_QUANTITY_QUEUE.getValue(), true, false, false, smallQuantityArgs);
    }

    private static void createDelayedQueues(Channel channel) throws IOException {
        Map<String, Object> bigQuantityArgs = new HashMap<>();
        bigQuantityArgs.put(X_DEAD_LETTER_EXCHANGE, JAVA_DIRECT_PRODUCT_EX.getValue());
        bigQuantityArgs.put(X_DEAD_LETTER_ROUTING_KEY, BIG_QUANTITY_KEY.getValue());
        channel.queueDeclare(BIG_QUANTITY_DELAYED_QUEUE.getValue(), true, false, false, bigQuantityArgs);

        Map<String, Object> smallQuantityArgs = new HashMap<>();
        smallQuantityArgs.put(X_DEAD_LETTER_EXCHANGE, JAVA_DIRECT_PRODUCT_EX.getValue());
        smallQuantityArgs.put(X_DEAD_LETTER_ROUTING_KEY, SMALL_QUANTITY_KEY.getValue());
        channel.queueDeclare(SMALL_QUANTITY_DELAYED_QUEUE.getValue(), true, false, false, smallQuantityArgs);
    }

    private static void createDlqQueues(Channel channel) throws IOException {
        channel.queueDeclare(BIG_QUANTITY_DLQ_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(SMALL_QUANTITY_DLQ_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        channel.queueBind(BIG_QUANTITY_QUEUE.getValue(), JAVA_DIRECT_PRODUCT_EX.getValue(), BIG_QUANTITY_KEY.getValue());
        channel.queueBind(SMALL_QUANTITY_QUEUE.getValue(), JAVA_DIRECT_PRODUCT_EX.getValue(), SMALL_QUANTITY_KEY.getValue());
    }

    private static void doDlqBinding(Channel channel) throws IOException {
        channel.queueBind(BIG_QUANTITY_DLQ_QUEUE.getValue(), DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), JAVA_ERRORS_BIG_QUANTITY_DLQ_KEY.getValue());
        channel.queueBind(SMALL_QUANTITY_DLQ_QUEUE.getValue(), DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), JAVA_ERRORS_SMALL_QUANTITY_DLQ_KEY.getValue());
    }

}
