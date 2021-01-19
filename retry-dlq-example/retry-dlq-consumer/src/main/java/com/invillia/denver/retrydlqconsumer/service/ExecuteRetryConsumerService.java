package com.invillia.denver.retrydlqconsumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invillia.denver.retrydlqconsumer.config.QueueConstants;
import com.invillia.denver.retrydlqconsumer.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ExecuteRetryConsumerService {


    private static final List<String> EXPIRATIONS = List.of("1000", "2000", "4000", "6000");

    private final Logger logger = LoggerFactory.getLogger(ExecuteRetryConsumerService.class);
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public ExecuteRetryConsumerService(ObjectMapper objectMapper,
                                       RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = QueueConstants.EXECUTE_RETRY_QUEUE)
    public void sampleFanoutQueueConsumer(Message message) throws IOException {
        var product = objectMapper.readValue(message.getBody(), Product.class);
        var routingKey = message.getMessageProperties().getReceivedRoutingKey();

        logger.info(QueueConstants.LOG_MESSAGE_X_DEATH,
                QueueConstants.LOOP_EXAMPLE_QUEUE, routingKey,
                getDeathValue(message.getMessageProperties().getHeaders()), product);

        if (product.getPrice().compareTo(BigDecimal.valueOf(30)) > 0) {
            retry(message);
        }
    }

    private void retry(Message message) {
        Message msg = MessageBuilder.fromMessage(message)
                .setExpiration(getNextTimeExpiration(message))
                .build();
        rabbitTemplate.send(QueueConstants.EXECUTE_RETRY_DELAYED, msg);
    }

    private String getNextTimeExpiration(Message message) {
        try {
            return EXPIRATIONS.get(getAttempt(message));
        } catch (IndexOutOfBoundsException e) {
            throw new AmqpRejectAndDontRequeueException("Limite de retentativas excedido!");
        }
    }

    private int getAttempt(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
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
