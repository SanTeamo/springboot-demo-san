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
        // ??? mandatory ?????????????????? true ???????????? exchange ??????????????????????????? routingKey ??????????????????????????? queue ???????????????
        // ??????broker ????????? basic.return?????????????????????????????????;
        // ??? mandatory ????????? false ???????????????????????? broker ????????????????????????;
        // ???????????????mandatory????????????broker??????????????????????????????route???????????????????????????????????????return????????????;
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
