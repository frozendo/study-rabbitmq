package com.frozendo.rabbit.errors.config;

import com.frozendo.rabbit.errors.domain.enums.DlqEnum;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.DEPARTMENT_BINDING_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.DEPARTMENT_PROMOTION_BINDING_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.DEPARTMENT_SPORT_BINDING_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.JAVA_ERRORS_PROMOTION_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.JAVA_ERRORS_REGISTER_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.JAVA_ERRORS_SPORT_DLQ_KEY;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.JAVA_TOPIC_PRODUCT_EX;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PRODUCT_REGISTER_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PRODUCT_REGISTER_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.PROMOTION_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.SPORT_DEPARTMENT_DLQ_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.SPORT_DEPARTMENT_QUEUE;

public class TopicExchangeConfig {

    public static void config(Channel channel) throws IOException {
        createDlqQueues(channel);
        doDlqBinding(channel);

        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private static void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(JAVA_TOPIC_PRODUCT_EX.getValue(), "topic", true);
    }

    private static void createQueues(Channel channel) throws IOException {
        Map<String, Object> registerArgs = new HashMap<>();
        registerArgs.put("x-dead-letter-exchange", DlqEnum.JAVA_ERRORS_DLQ_EX.getValue());
        registerArgs.put("x-dead-letter-routing-key", JAVA_ERRORS_REGISTER_DLQ_KEY.getValue());
        channel.queueDeclare(PRODUCT_REGISTER_QUEUE.getValue(), true, false, false, registerArgs);

        Map<String, Object> sportArgs = new HashMap<>();
        sportArgs.put("x-dead-letter-exchange", DlqEnum.JAVA_ERRORS_DLQ_EX.getValue());
        sportArgs.put("x-dead-letter-routing-key", JAVA_ERRORS_SPORT_DLQ_KEY.getValue());
        channel.queueDeclare(SPORT_DEPARTMENT_QUEUE.getValue(), true, false, false, sportArgs);

        Map<String, Object> promotionArgs = new HashMap<>();
        promotionArgs.put("x-dead-letter-exchange", DlqEnum.JAVA_ERRORS_DLQ_EX.getValue());
        promotionArgs.put("x-dead-letter-routing-key", JAVA_ERRORS_PROMOTION_DLQ_KEY.getValue());
        channel.queueDeclare(PROMOTION_QUEUE.getValue(), true, false, false, promotionArgs);
    }

    private static void createDlqQueues(Channel channel) throws IOException {
        channel.queueDeclare(PRODUCT_REGISTER_DLQ_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(SPORT_DEPARTMENT_DLQ_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(PROMOTION_DLQ_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        channel.queueBind(PRODUCT_REGISTER_QUEUE.getValue(), JAVA_TOPIC_PRODUCT_EX.getValue(), DEPARTMENT_BINDING_KEY.getValue());
        channel.queueBind(SPORT_DEPARTMENT_QUEUE.getValue(), JAVA_TOPIC_PRODUCT_EX.getValue(), DEPARTMENT_SPORT_BINDING_KEY.getValue());
        channel.queueBind(PROMOTION_QUEUE.getValue(), JAVA_TOPIC_PRODUCT_EX.getValue(), DEPARTMENT_PROMOTION_BINDING_KEY.getValue());
    }

    private static void doDlqBinding(Channel channel) throws IOException {
        channel.queueBind(PRODUCT_REGISTER_DLQ_QUEUE.getValue(), DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), JAVA_ERRORS_REGISTER_DLQ_KEY.getValue());
        channel.queueBind(SPORT_DEPARTMENT_DLQ_QUEUE.getValue(), DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), JAVA_ERRORS_SPORT_DLQ_KEY.getValue());
        channel.queueBind(PROMOTION_DLQ_QUEUE.getValue(), DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), JAVA_ERRORS_PROMOTION_DLQ_KEY.getValue());
    }
}
