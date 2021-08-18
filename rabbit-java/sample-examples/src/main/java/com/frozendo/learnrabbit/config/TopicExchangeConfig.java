package com.frozendo.learnrabbit.config;

import com.rabbitmq.client.Channel;

import java.io.IOException;

import static com.frozendo.learnrabbit.domain.TopicEnum.DEPARTMENT_BINDING_KEY;
import static com.frozendo.learnrabbit.domain.TopicEnum.DEPARTMENT_PROMOTION_BINDING_KEY;
import static com.frozendo.learnrabbit.domain.TopicEnum.DEPARTMENT_SPORT_BINDING_KEY;
import static com.frozendo.learnrabbit.domain.TopicEnum.JAVA_TOPIC_PRODUCT_EX;
import static com.frozendo.learnrabbit.domain.TopicEnum.PRODUCT_REGISTER_ELECTRONIC_QUEUE;
import static com.frozendo.learnrabbit.domain.TopicEnum.PRODUCT_REGISTER_QUEUE;
import static com.frozendo.learnrabbit.domain.TopicEnum.PRODUCT_REGISTER_SPORT_QUEUE;

public class TopicExchangeConfig {

    public static void config(Channel channel) throws IOException {
        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private static void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(JAVA_TOPIC_PRODUCT_EX.getValue(), "topic", true);
    }

    private static void createQueues(Channel channel) throws IOException {
        channel.queueDeclare(PRODUCT_REGISTER_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(PRODUCT_REGISTER_SPORT_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(PRODUCT_REGISTER_ELECTRONIC_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        channel.queueBind(PRODUCT_REGISTER_QUEUE.getValue(), JAVA_TOPIC_PRODUCT_EX.getValue(), DEPARTMENT_BINDING_KEY.getValue());
        channel.queueBind(PRODUCT_REGISTER_SPORT_QUEUE.getValue(), JAVA_TOPIC_PRODUCT_EX.getValue(), DEPARTMENT_SPORT_BINDING_KEY.getValue());
        channel.queueBind(PRODUCT_REGISTER_ELECTRONIC_QUEUE.getValue(), JAVA_TOPIC_PRODUCT_EX.getValue(), DEPARTMENT_PROMOTION_BINDING_KEY.getValue());
    }
}
