package com.frozendo.rabbit.sample.consumer.topic;

import com.frozendo.rabbit.sample.domain.Product;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.frozendo.rabbit.sample.domain.enums.TopicEnum.SPORT_DEPARTMENT_QUEUE;

@Component
public class SportDepartmentConsumerService {

    Logger log = LoggerFactory.getLogger(SportDepartmentConsumerService.class);

    @RabbitListener(queues = "spring-sample-topic-sport-department-queue")
    public void consumer(Message message) {
        var product = (Product) SerializationUtils.deserialize(message.getBody());
        log.info("queue {}, value read = {}", SPORT_DEPARTMENT_QUEUE.getValue(), product);
        log.info("message routingKey = {}", message.getMessageProperties().getReceivedRoutingKey());
    }

}
