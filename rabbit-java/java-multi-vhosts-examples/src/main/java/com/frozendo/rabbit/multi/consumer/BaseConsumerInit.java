package com.frozendo.rabbit.multi.consumer;

import com.rabbitmq.client.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BaseConsumerInit {

    private static final Logger log = LoggerFactory.getLogger(BaseConsumerInit.class);

    private BaseConsumerInit() {}

    public static void init(DefaultConsumer consumer, String queue) {
        try {
            consumer.getChannel().basicConsume(queue, false, queue.concat("-multi-tag"), consumer);
        } catch (IOException ex) {
            log.error("Rabbit error");
            ex.printStackTrace();
        } catch (Exception ex) {
            log.error("General error");
            ex.printStackTrace();
        }
    }

}
