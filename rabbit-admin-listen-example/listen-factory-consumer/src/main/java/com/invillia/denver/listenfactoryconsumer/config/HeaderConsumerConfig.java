package com.invillia.denver.listenfactoryconsumer.config;

import com.invillia.denver.listenfactoryconsumer.listener.AdminListenHeaderListener;
import com.invillia.denver.listenfactoryconsumer.listener.OtherAdminListenHeaderListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderConsumerConfig {

    private final ConnectionFactory connectionFactory;
    private final AdminListenHeaderListener adminListenHeaderListener;
    private final OtherAdminListenHeaderListener otherAdminListenHeaderListener;

    public HeaderConsumerConfig(ConnectionFactory connectionFactory,
                                AdminListenHeaderListener adminListenHeaderListener,
                                OtherAdminListenHeaderListener otherAdminListenHeaderListener) {
        this.connectionFactory = connectionFactory;
        this.adminListenHeaderListener = adminListenHeaderListener;
        this.otherAdminListenHeaderListener = otherAdminListenHeaderListener;
    }

    @Bean
    public MessageListenerContainer adminHeaderConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.ADMIN_LISTEN_HEADER_QUEUE);
        container.setMessageListener(adminListenHeaderListener);
        container.start();
        return container;
    }

    @Bean
    public MessageListenerContainer otherAdminHeaderConfig() {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QueueConstants.OTHER_ADMIN_LISTEN_HEADER_QUEUE);
        container.setMessageListener(otherAdminListenHeaderListener);
        container.start();
        return container;
    }
}
