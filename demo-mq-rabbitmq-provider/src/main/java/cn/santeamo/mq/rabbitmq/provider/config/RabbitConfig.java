package cn.santeamo.mq.rabbitmq.provider.config;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author santeamo
 */
@Slf4j
@Configuration
public class RabbitConfig {

    public static final String DEFAULT_CONNECTION_FACTORY = RabbitConst.Default.CONNECTION_FACTORY;
    public static final String OTHER_CONNECTION_FACTORY = RabbitConst.Other.CONNECTION_FACTORY;

    @Bean(DEFAULT_CONNECTION_FACTORY)
    @Primary
    public ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
        CachingConnectionFactory cachingConnectionFactory = getDefaultConnectFactory(rabbitProperties);
        cachingConnectionFactory.setVirtualHost(RabbitConst.Default.VIRTUAL_HOST);
        return cachingConnectionFactory;
    }

    @Bean(OTHER_CONNECTION_FACTORY)
    public ConnectionFactory otherConnectionFactory(RabbitProperties rabbitProperties) {
        CachingConnectionFactory cachingConnectionFactory = getDefaultConnectFactory(rabbitProperties);
        cachingConnectionFactory.setVirtualHost(RabbitConst.Other.VIRTUAL_HOST);
        return cachingConnectionFactory;
    }

    private CachingConnectionFactory getDefaultConnectFactory(RabbitProperties rabbitProperties) {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(rabbitProperties.getHost());
        factory.setPort(rabbitProperties.getPort());
        factory.setUsername(rabbitProperties.getUsername());
        factory.setPassword(rabbitProperties.getPassword());
        factory.setVirtualHost(rabbitProperties.getVirtualHost());
        factory.setPublisherConfirms(rabbitProperties.isPublisherConfirms());
        factory.setPublisherReturns(rabbitProperties.isPublisherReturns());
        return factory;
    }

    @Bean(RabbitConst.Default.RABBIT_TEMPLATE)
    @Primary
    public RabbitTemplate defaultRabbitTemplate(@Qualifier(DEFAULT_CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        return commonRabbitTemplate(RabbitConst.Default.RABBIT_TEMPLATE, connectionFactory);
    }

    @Bean(RabbitConst.Other.RABBIT_TEMPLATE)
    public RabbitTemplate otherRabbitTemplate(@Qualifier(OTHER_CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        return commonRabbitTemplate(RabbitConst.Other.RABBIT_TEMPLATE, connectionFactory);
    }

    private RabbitTemplate commonRabbitTemplate(String rabbitTemplateName, ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 当 mandatory 标志位设置为 true 时，如果 exchange 根据自身类型和消息 routingKey 无法找到一个合适的 queue 存储消息，
        // 那么broker 会调用 basic.return方法将消息返还给生产者;
        // 当 mandatory 设置为 false 时，出现上述情况 broker 会直接将消息丢弃;
        // 通俗的讲，mandatory标志告诉broker代理服务器至少将消息route到一个队列中，否则就将消息return给发送者;
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("{} ConfirmCallback ===> [ correlationData({}), ack({}), cause({}) ]",
                rabbitTemplateName, correlationData, ack, cause);
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("{} ReturnCallback ===> [ message({}), replyCode({}), replyText({}), exchange({}), routingKey({}) ]",
                rabbitTemplateName, message, replyCode, replyText, exchange, routingKey);
        });
        return rabbitTemplate;
    }
}
