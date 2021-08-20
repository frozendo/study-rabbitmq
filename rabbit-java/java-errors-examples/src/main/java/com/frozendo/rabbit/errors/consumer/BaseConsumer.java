package com.frozendo.rabbit.errors.consumer;

import com.rabbitmq.client.DefaultConsumer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
public class BaseConsumer {

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

    public static double getRandomNumber() {
        return new Random().nextInt(100);
    }

}
