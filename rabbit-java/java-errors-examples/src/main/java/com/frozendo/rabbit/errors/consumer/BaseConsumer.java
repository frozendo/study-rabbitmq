package com.frozendo.rabbit.errors.consumer;

import com.frozendo.rabbit.errors.domain.Product;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Component
public class BaseConsumer {

    Logger log = LoggerFactory.getLogger(BaseConsumer.class);

    private static final List<String> EXPIRATIONS = List.of("10000", "15000");

    public void init(DefaultConsumer consumer, String queue) {
        try {
            consumer.getChannel().basicConsume(queue, false, queue.concat("-errors-tag"), consumer);
        } catch (IOException ex) {
            System.out.println("Rabbit error");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General error");
            ex.printStackTrace();
        }
    }

    public double getRandomNumber() {
        return new Random().nextInt(100);
    }

    public void rejectOrRequeueMessage(Channel channel, Product product, AMQP.BasicProperties properties,
                                       String delayedQueue, Long deliveryTag) throws IOException {
        var isSendToDelayed = retry(channel, product, properties, delayedQueue);
        if (isSendToDelayed) {
            channel.basicAck(deliveryTag, false);
        } else {
            log.info("reject rabbit message");
            channel.basicReject(deliveryTag, false);
        }
    }

    private boolean retry(Channel channel, Product product, AMQP.BasicProperties properties, String delayedQueue) {
        var expiration = getNextTimeExpiration(properties);

        if (expiration.isEmpty()) {
            log.info("Maximum attempts reached");
            return false;
        }

        var basicProperties = new AMQP.BasicProperties.Builder()
                .contentType(properties.getContentType())
                .headers(properties.getHeaders())
                .expiration(expiration)
                .build();

        sendToDelayedMessage(channel, product, delayedQueue, basicProperties);

        return true;
    }

    private void sendToDelayedMessage(Channel channel, Product product, String delayedQueue, AMQP.BasicProperties properties) {
        log.info("Send to delayed = {}", delayedQueue);
        try {
            channel.basicPublish(StringUtils.EMPTY,
                    delayedQueue,
                    properties,
                    product.toByteArray());
        } catch (IOException ex) {
            System.out.println("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General exception");
            ex.printStackTrace();
        }
    }

    private String getNextTimeExpiration(AMQP.BasicProperties properties) {
        try {
            return EXPIRATIONS.get(getAttempt(properties));
        } catch (IndexOutOfBoundsException e) {
            return StringUtils.EMPTY;
        }
    }

    private int getAttempt(AMQP.BasicProperties properties) {
        Map<String, Object> headers = properties.getHeaders();
        if (headers == null) {
            return 0;
        }
        return getDeathValue(headers);
    }

    private int getDeathValue(Map<String, Object> headers) {
        List<Map<String, Object>> deaths = (List<Map<String, Object>>) headers.get("x-death");
        if (Objects.isNull(deaths) || deaths.isEmpty()) {
            return 0;
        }
        Map<String, Object> death = deaths.get(0);
        return ((Long) death.get("count")).intValue();
    }

}
