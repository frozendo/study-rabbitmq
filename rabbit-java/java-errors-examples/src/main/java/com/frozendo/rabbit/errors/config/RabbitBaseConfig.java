package com.frozendo.rabbit.errors.config;

import com.frozendo.rabbit.errors.domain.enums.DlqEnum;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Component
public class RabbitBaseConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitBaseConfig.class);

    private static Channel channel;

    @PostConstruct
    public void init() {
        startConnection();
        configRabbit();
    }

    public Channel getChannel() {
        if (Objects.isNull(channel)) {
            startConnection();
        }
        return channel;
    }

    private void configRabbit() {
        try {
            createApplicationDlq(channel);
            DirectExchangeConfig.config(channel);
            TopicExchangeConfig.config(channel);
            FanoutExchangeConfig.config(channel);
            HeaderExchangeConfig.config(channel);
        } catch (IOException ex) {
            log.error("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            log.error("General exception");
            ex.printStackTrace();
        }
    }

    private static void startConnection() {
        var factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("test12");
        try {
            channel = factory.newConnection().createChannel();
        } catch (IOException | TimeoutException ex) {
            log.error("Rabbit exception");
            ex.printStackTrace();
        } catch (Exception ex) {
            log.error("General exception");
            ex.printStackTrace();
        }
    }

    private void createApplicationDlq(Channel channel) throws IOException {
        channel.exchangeDeclare(DlqEnum.JAVA_ERRORS_DLQ_EX.getValue(), "direct", true);
    }

}
