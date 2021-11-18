package cn.santeamo.mq.rabbitmq.provider.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author santeamo
 */
@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 当 mandatory 标志位设置为 true 时，如果 exchange 根据自身类型和消息 routingKey 无法找到一个合适的 queue 存储消息，
        // 那么broker 会调用 basic.return方法将消息返还给生产者;
        // 当 mandatory 设置为 false 时，出现上述情况 broker 会直接将消息丢弃;
        // 通俗的讲，mandatory标志告诉broker代理服务器至少将消息route到一个队列中，否则就将消息return给发送者;
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback ===> [ correlationData({}), ack({}), cause({}) ]", correlationData, ack, cause);
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("ReturnCallback ===> [ message({}), replyCode({}), replyText({}), exchange({}), routingKey({}) ]",
                message, replyCode, replyText, exchange, routingKey);
        });
        return rabbitTemplate;
    }
}
