package cn.santeamo.mq.rabbitmq.consumer.config;

import cn.santeamo.mq.rabbitmq.consumer.listener.RabbitAckListener;
import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息监听设置
 * @author santeamo
 */
@Configuration
@RequiredArgsConstructor
public class MessageListenerConfig {

    private final CachingConnectionFactory cachingConnectionFactory;
    private final RabbitAckListener rabbitAckListener;

//    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(cachingConnectionFactory);
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setQueueNames(RabbitConst.Direct.QUEUE);
        simpleMessageListenerContainer.setMessageListener(rabbitAckListener);
        return simpleMessageListenerContainer;
    }
}
