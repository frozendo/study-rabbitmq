package com.frozendo.rabbit.multi.consumer;

import com.rabbitmq.client.DefaultConsumer;

import java.io.IOException;

public class BaseConsumerInit {

    public static void init(DefaultConsumer consumer, String queue) {
        try {
            consumer.getChannel().basicConsume(queue, false, queue.concat("-multi-tag"), consumer);
        } catch (IOException ex) {
            System.out.println("Rabbit error");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("General error");
            ex.printStackTrace();
        }
    }

}
