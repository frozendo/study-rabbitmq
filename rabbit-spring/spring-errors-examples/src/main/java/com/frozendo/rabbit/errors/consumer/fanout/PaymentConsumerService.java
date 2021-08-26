package com.frozendo.rabbit.errors.consumer.fanout;

import com.frozendo.rabbit.errors.consumer.BaseConsumer;
import com.frozendo.rabbit.errors.domain.Product;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.FanoutEnum.PAYMENT_QUEUE;

@Component
public class PaymentConsumerService {

    Logger log = LoggerFactory.getLogger(PaymentConsumerService.class);

    private final BaseConsumer baseConsumer;

    public PaymentConsumerService(BaseConsumer baseConsumer) {
        this.baseConsumer = baseConsumer;
    }

    @RabbitListener(queues = "spring-errors-fanout-payment-queue")
    public void consumer(Message message) {
        var product = (Product) SerializationUtils.deserialize(message.getBody());
        log.info("queue {}, value read = {}", PAYMENT_QUEUE.getValue(), product);
        log.info("message routingKey = {}", message.getMessageProperties().getReceivedRoutingKey());
        var value = baseConsumer.getRandomNumber();
        if (value % 2 != 0) {
            baseConsumer.rejectOrRequeueMessage(PAYMENT_DELAYED_QUEUE.getValue(), message);
        }
    }

}
