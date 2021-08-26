package com.frozendo.rabbit.multi.config;

import com.frozendo.rabbit.multi.domain.enums.TopicEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class TopicExchangeConfig {

    private static final Logger log = LoggerFactory.getLogger(TopicExchangeConfig.class);

    private static Channel channel;

    public TopicExchangeConfig() {
        try {
            startConnection();
            config();
        } catch (IOException | TimeoutException ex) {
            log.error("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            log.error("General exception");
            ex.printStackTrace();
        }
    }

    public static Channel getChannel() {
        try {
            if (channel == null) {
                startConnection();
            }
            return channel;
        } catch (IOException | TimeoutException ex) {
            log.error("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            log.error("General exception");
            ex.printStackTrace();
        }
        return null;
    }

    private static void startConnection() throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setUsername("user-topic");
        factory.setPassword("test12");
        factory.setVirtualHost("/vtopic");
        var connection = factory.newConnection();
        channel = connection.createChannel();
    }

    private void config() throws IOException {
        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue(), "topic", true);
    }

    private void createQueues(Channel channel) throws IOException {
        channel.queueDeclare(TopicEnum.PRODUCT_REGISTER_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(TopicEnum.SPORT_DEPARTMENT_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(TopicEnum.PROMOTION_QUEUE.getValue(), true, false, false, null);
    }

    private void doBinding(Channel channel) throws IOException {
        channel.queueBind(TopicEnum.PRODUCT_REGISTER_QUEUE.getValue(), TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue(), TopicEnum.DEPARTMENT_BINDING_KEY.getValue());
        channel.queueBind(TopicEnum.SPORT_DEPARTMENT_QUEUE.getValue(), TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue(), TopicEnum.DEPARTMENT_SPORT_BINDING_KEY.getValue());
        channel.queueBind(TopicEnum.PROMOTION_QUEUE.getValue(), TopicEnum.JAVA_TOPIC_PRODUCT_EX.getValue(), TopicEnum.DEPARTMENT_PROMOTION_BINDING_KEY.getValue());
    }
}
