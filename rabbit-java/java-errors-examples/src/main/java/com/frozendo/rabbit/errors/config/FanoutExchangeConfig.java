package com.frozendo.rabbit.errors.config;

import com.frozendo.rabbit.errors.domain.enums.DlqEnum;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.INVENTORY_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.INVENTORY_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.INVENTORY_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.JAVA_ERRORS_INVENTORY_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.JAVA_ERRORS_PAYMENT_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.JAVA_FANOUT_PRODUCT_EX;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_QUEUE;

public class FanoutExchangeConfig {

    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    private FanoutExchangeConfig() {}

    public static void config(Channel channel) throws IOException {
        createDlqQueues(channel);
        doDlqBinding(channel);

        createExchange(channel);
        createQueues(channel);
        createDelayedQueues(channel);
        doBinding(channel);
    }

    private static void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(JAVA_FANOUT_PRODUCT_EX.getValue(), "fanout", true);
    }

    private static void createQueues(Channel channel) throws IOException {
        Map<String, Object> inventoryArgs = new HashMap<>();
        inventoryArgs.put(X_DEAD_LETTER_EXCHANGE, DlqEnum.JAVA_ERRORS_DLQ_EX.getValue());
        inventoryArgs.put(X_DEAD_LETTER_ROUTING_KEY, JAVA_ERRORS_INVENTORY_DLQ_KEY.getValue());
        channel.queueDeclare(INVENTORY_QUEUE.getValue(), true, false, false, inventoryArgs);

        Map<String, Object> paymentArgs = new HashMap<>();
        paymentArgs.put(X_DEAD_LETTER_EXCHANGE, DlqEnum.JAVA_ERRORS_DLQ_EX.getValue());
        paymentArgs.put(X_DEAD_LETTER_ROUTING_KEY, JAVA_ERRORS_PAYMENT_DLQ_KEY.getValue());
        channel.queueDeclare(PAYMENT_QUEUE.getValue(), true, false, false, paymentArgs);
    }

    private static void createDelayedQueues(Channel channel) throws IOException {
        Map<String, Object> inventoryArgs = new HashMap<>();
        inventoryArgs.put(X_DEAD_LETTER_EXCHANGE, StringUtils.EMPTY);
        inventoryArgs.put(X_DEAD_LETTER_ROUTING_KEY, INVENTORY_QUEUE.getValue());
        channel.queueDeclare(INVENTORY_DELAYED_QUEUE.getValue(), true, false, false, inventoryArgs);

        Map<String, Object> paymentArgs = new HashMap<>();
        paymentArgs.put(X_DEAD_LETTER_EXCHANGE, StringUtils.EMPTY);
        paymentArgs.put(X_DEAD_LETTER_ROUTING_KEY, PAYMENT_QUEUE.getValue());
        channel.queueDeclare(PAYMENT_DELAYED_QUEUE.getValue(), true, false, false, paymentArgs);
    }

    private static void createDlqQueues(Channel channel) throws IOException {
        channel.queueDeclare(INVENTORY_DLQ_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(PAYMENT_DLQ_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        channel.queueBind(INVENTORY_QUEUE.getValue(), JAVA_FANOUT_PRODUCT_EX.getValue(), StringUtils.EMPTY);
        channel.queueBind(PAYMENT_QUEUE.getValue(), JAVA_FANOUT_PRODUCT_EX.getValue(), StringUtils.EMPTY);
    }

    private static void doDlqBinding(Channel channel) throws IOException {
        channel.queueBind(INVENTORY_DLQ_QUEUE.getValue(), DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), JAVA_ERRORS_INVENTORY_DLQ_KEY.getValue());
        channel.queueBind(PAYMENT_DLQ_QUEUE.getValue(), DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), JAVA_ERRORS_PAYMENT_DLQ_KEY.getValue());
    }
}
