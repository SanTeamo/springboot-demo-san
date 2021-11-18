package cn.santeamo.mq.rabbitmq.provider.config;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题型
 * @author shenle
 */
@Configuration
public class TopicRabbitConfig {

    @Bean
    public Queue topicOneQueue() {
        return new Queue(RabbitConst.Topic.One.QUEUE);
    }

    @Bean
    public Queue topicTwoQueue() {
        return new Queue(RabbitConst.Topic.Two.QUEUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConst.Topic.EXCHANGE);
    }

    /**
     * 将 topic.one 和 交换机 topicExchange 绑定，且路由键为 topic.one
     * 这样消息携带的路由键为 topic.one 的，都会分发到该队列
     * @return {@link Binding}
     */
    @Bean
    public Binding topicOneBinding() {
        return BindingBuilder.bind(topicOneQueue()).to(topicExchange()).with(RabbitConst.Topic.One.ROUTING_KEY);
    }

    /**
     * 将 topic.two 和 交换机 topicExchange 绑定，且路由键为 topic.#
     * 这样消息携带的路由键为 topic. 开头的，都会分发到该队列
     * @return {@link Binding}
     */
    @Bean
    public Binding topicTwoBinding() {
        return BindingBuilder.bind(topicTwoQueue()).to(topicExchange()).with(RabbitConst.Topic.RoutingKey.ALL);
    }
}
