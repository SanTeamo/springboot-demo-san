package cn.santeamo.mq.rabbitmq.consumer.config;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq 配置 virtual-host = "/other"
 * @author santeamo
 */
@Configuration
public class RabbitConfigOther {

    public static final String CONNECTION_FACTORY = RabbitConst.Other.CONNECTION_FACTORY;

    @Bean(CONNECTION_FACTORY)
    public ConnectionFactory otherConnectionFactory(RabbitProperties rabbitProperties) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(rabbitProperties.getHost());
        cachingConnectionFactory.setPort(rabbitProperties.getPort());
        cachingConnectionFactory.setUsername(rabbitProperties.getUsername());
        cachingConnectionFactory.setPassword(rabbitProperties.getPassword());
        cachingConnectionFactory.setVirtualHost(RabbitConst.Other.VIRTUAL_HOST);
        return cachingConnectionFactory;
    }

    @Bean(RabbitConst.Other.RABBIT_LISTENER_CONTAINER)
    public SimpleRabbitListenerContainerFactory ucRabbitListenerContainer(
        SimpleRabbitListenerContainerFactoryConfigurer configurer,
        @Qualifier(CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
