package com.frozendo.rabbit.sample.config;

import com.frozendo.rabbit.sample.domain.enums.TopicEnum;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class TopicExchangeConfig {

    public static void config(Channel channel) throws IOException {
        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private static void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue(), "topic", true);
    }

    private static void createQueues(Channel channel) throws IOException {
        channel.queueDeclare(TopicEnum.PRODUCT_REGISTER_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(TopicEnum.SPORT_DEPARTMENT_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(TopicEnum.PROMOTION_QUEUE.getValue(), true, false, false, null);
    }

    private static void doBinding(Channel channel) throws IOException {
        channel.queueBind(TopicEnum.PRODUCT_REGISTER_QUEUE.getValue(), TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue(), TopicEnum.DEPARTMENT_BINDING_KEY.getValue());
        channel.queueBind(TopicEnum.SPORT_DEPARTMENT_QUEUE.getValue(), TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue(), TopicEnum.DEPARTMENT_SPORT_BINDING_KEY.getValue());
        channel.queueBind(TopicEnum.PROMOTION_QUEUE.getValue(), TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue(), TopicEnum.DEPARTMENT_PROMOTION_BINDING_KEY.getValue());
    }
}
