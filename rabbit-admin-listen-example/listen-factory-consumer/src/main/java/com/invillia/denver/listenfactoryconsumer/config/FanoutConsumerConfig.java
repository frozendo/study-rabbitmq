package com.invillia.denver.listenfactoryconsumer.config;

import com.invillia.denver.listenfactoryconsumer.listener.AdminListenFanoutListener;
import com.invillia.denver.listenfactoryconsumer.listener.OtherAdminListenFanoutListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConsumerConfig {

    private final ConnectionFactory connectionFactory;
    private final AdminListenFanoutListener adminListenFanoutListener;
    private final OtherAdminListenFanoutListener otherAdminListenFanoutListener;

    public FanoutConsumerConfig(ConnectionFactory connectionFactory,
                                AdminListenFanoutListener adminListenFanoutListener,
                                OtherAdminListenFanoutListener otherAdminListenFanoutListener) {
        this.connectionFactory = connectionFactory;
        this.adminListenFanoutListener = adminListenFanoutListener;
        this.otherAdminListenFanoutListener = otherAdminListenFanoutListener;
    }

    @Bean
    public MessageListenerContainer adminFanoutConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.ADMIN_LISTEN_FANOUT_QUEUE);
        container.setMessageListener(adminListenFanoutListener);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer otherAdminFanoutConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.OTHER_ADMIN_LISTEN_FANOUT_QUEUE);
        container.setMessageListener(otherAdminListenFanoutListener);
        container.start();
        return container;
    }

}
