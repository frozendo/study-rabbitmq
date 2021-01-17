package com.invillia.denver.listenfactoryconsumer.config;

import com.invillia.denver.listenfactoryconsumer.listener.AdminListenDirectListener;
import com.invillia.denver.listenfactoryconsumer.listener.OtherAdminListenDirectListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConsumerConfig {

    private final ConnectionFactory connectionFactory;
    private final AdminListenDirectListener adminListenDirectListener;
    private final OtherAdminListenDirectListener otherAdminListenDirectListener;

    public DirectConsumerConfig(ConnectionFactory connectionFactory,
                                AdminListenDirectListener adminListenDirectListener,
                                OtherAdminListenDirectListener otherAdminListenDirectListener) {
        this.connectionFactory = connectionFactory;
        this.adminListenDirectListener = adminListenDirectListener;
        this.otherAdminListenDirectListener = otherAdminListenDirectListener;
    }

    @Bean
    public MessageListenerContainer adminDirectConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.ADMIN_LISTEN_DIRECT_QUEUE);
        container.setMessageListener(adminListenDirectListener);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer otherAdminDirectConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.OTHER_ADMIN_LISTEN_DIRECT_QUEUE);
        container.setMessageListener(otherAdminListenDirectListener);
        container.start();
        return container;
    }
}
