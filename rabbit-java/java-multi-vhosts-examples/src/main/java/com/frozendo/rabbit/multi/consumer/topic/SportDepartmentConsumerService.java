package com.frozendo.rabbit.multi.consumer.topic;

import com.frozendo.rabbit.multi.config.TopicExchangeConfig;
import com.frozendo.rabbit.multi.consumer.BaseConsumerInit;
import com.frozendo.rabbit.multi.domain.Product;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.frozendo.rabbit.multi.domain.enums.TopicEnum.SPORT_DEPARTMENT_QUEUE;

@Component
public class SportDepartmentConsumerService extends DefaultConsumer {

    Logger log = LoggerFactory.getLogger(SportDepartmentConsumerService.class);

    public SportDepartmentConsumerService() {
        super(TopicExchangeConfig.getChannel());
        BaseConsumerInit.init(this, SPORT_DEPARTMENT_QUEUE.getValue());
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        var product = (Product) SerializationUtils.deserialize(body);
        log.info("queue {}, value read = {}", SPORT_DEPARTMENT_QUEUE.getValue(), product);
        log.info("message routingKey = {}", envelope.getRoutingKey());
        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

}
