package com.frozendo.rabbit.errors.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Component
public class BaseConsumer {

    Logger log = LoggerFactory.getLogger(BaseConsumer.class);

    private static final List<String> EXPIRATIONS = List.of("1000", "2000", "5000", "10000");

    private final RabbitTemplate rabbitTemplate;

    public BaseConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public double getRandomNumber() {
        return new Random().nextInt(100);
    }

    public void rejectOrRequeueMessage(String delayedQueue, Message message) {
        log.info("Send to delayed = {}", delayedQueue);
        rabbitTemplate.convertAndSend(delayedQueue, getUpdatedMessage(message));
    }

    private Message getUpdatedMessage(Message message) {
        return MessageBuilder.fromMessage(message)
                .setExpiration(getNextTimeExpiration(message))
                .build();
    }

    private String getNextTimeExpiration(Message message) {
        try {
            return EXPIRATIONS.get(getAttempt(message));
        } catch (IndexOutOfBoundsException e) {
            log.info("Maximum attempts reached, send to dlq!");
            throw new AmqpRejectAndDontRequeueException("send message to dlq!");
        }
    }

    private int getAttempt(Message message) {
        var headers = message.getMessageProperties().getHeaders();
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
