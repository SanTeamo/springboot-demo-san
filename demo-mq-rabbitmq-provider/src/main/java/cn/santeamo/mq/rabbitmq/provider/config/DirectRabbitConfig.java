package cn.santeamo.mq.rabbitmq.provider.config;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 直连型
 * @author santeamo
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 直连型队列
     * @return {@link org.springframework.amqp.core.Queue}
     */
    @Bean
    public Queue directQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        // 一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(RabbitConst.Direct.QUEUE, true);
    }

    /**
     * 直连型交换机
     * @return {@link org.springframework.amqp.core.DirectExchange}
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(RabbitConst.Direct.EXCHANGE, true, false);
    }

    /**
     * 绑定队列和交换机，并设置匹配键
     * @return {@link org.springframework.amqp.core.Binding}
     */
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(RabbitConst.Direct.ROUTING_KEY);
    }

    /**
     * 设置一个交换机，不设置 routing key
     */
    @Bean
    DirectExchange nonRoutingKeyDirectExchange() {
        return new DirectExchange(RabbitConst.NonRoutingKeyDirect.EXCHANGE);
    }

}
