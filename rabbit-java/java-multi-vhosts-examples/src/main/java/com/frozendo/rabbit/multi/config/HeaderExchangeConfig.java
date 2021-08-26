package com.frozendo.rabbit.multi.config;

import com.frozendo.rabbit.multi.domain.enums.HeaderEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Component
public class HeaderExchangeConfig {

    private static final Logger log = LoggerFactory.getLogger(HeaderExchangeConfig.class);

    private static Channel channel;

    public HeaderExchangeConfig() {
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
        factory.setUsername("user-header");
        factory.setPassword("test12");
        factory.setVirtualHost("/vheader");
        var connection = factory.newConnection();
        channel = connection.createChannel();
    }

    private void config() throws IOException {
        createExchange(channel);
        createQueues(channel);
        doBinding(channel);
    }

    private void createExchange(Channel channel) throws IOException {
        channel.exchangeDeclare(HeaderEnum.JAVA_HEADER_PRODUCT_EX.getValue(), "headers", true);
    }

    private void createQueues(Channel channel) throws IOException {
        channel.queueDeclare(HeaderEnum.BIG_GIFT_QUEUE.getValue(), true, false, false, null);
        channel.queueDeclare(HeaderEnum.SMALL_GIFT_QUEUE.getValue(), true, false, false, null);
    }

    private void doBinding(Channel channel) throws IOException {
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
