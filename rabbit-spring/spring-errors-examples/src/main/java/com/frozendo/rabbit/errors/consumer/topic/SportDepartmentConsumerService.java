package com.frozendo.rabbit.errors.consumer.topic;

import com.frozendo.rabbit.errors.consumer.BaseConsumer;
import com.frozendo.rabbit.errors.domain.Product;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.SPORT_DEPARTMENT_DELAYED_QUEUE;
import static com.frozendo.rabbit.errors.domain.enums.TopicEnum.SPORT_DEPARTMENT_QUEUE;

@Component
public class SportDepartmentConsumerService {

    Logger log = LoggerFactory.getLogger(SportDepartmentConsumerService.class);

    private final BaseConsumer baseConsumer;

    public SportDepartmentConsumerService(BaseConsumer baseConsumer) {
        this.baseConsumer = baseConsumer;
    }

    @RabbitListener(queues = "spring-errors-topic-sport-department-queue")
    public void consumer(Message message) {
        var product = (Product) SerializationUtils.deserialize(message.getBody());
        log.info("queue {}, value read = {}", SPORT_DEPARTMENT_QUEUE.getValue(), product);
        log.info("message routingKey = {}", message.getMessageProperties().getReceivedRoutingKey());
        var value = baseConsumer.getRandomNumber();
        if (value % 2 != 0) {
            baseConsumer.rejectOrRequeueMessage(SPORT_DEPARTMENT_DELAYED_QUEUE.getValue(), message);
        }
    }

}
