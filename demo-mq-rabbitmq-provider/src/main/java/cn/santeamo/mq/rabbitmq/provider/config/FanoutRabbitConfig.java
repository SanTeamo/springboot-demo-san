package cn.santeamo.mq.rabbitmq.provider.config;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 扇型
 * @author shenle
 */
@Configuration
public class FanoutRabbitConfig {

    /**
     *  创建三个队列 ：fanout.one   fanout.two  fanout.three
     *  将三个队列都绑定在交换机 fanoutExchange 上
     *  因为是扇型交换机，路由键无需配置，配置也不起作用
     */
    @Bean
    public Queue fanoutOneQueue() {
        return new Queue(RabbitConst.Fanout.One.QUEUE);
    }

    @Bean
    public Queue fanoutTwoQueue() {
        return new Queue(RabbitConst.Fanout.Two.QUEUE);
    }

    @Bean
    public Queue fanoutThreeQueue() {
        return new Queue(RabbitConst.Fanout.Three.QUEUE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConst.Fanout.EXCHANGE);
    }

    /**
     * 将 三个队列 和 扇形交换机 fanoutExchange 绑定
     * @return {@link Binding}
     */
    @Bean
    public Binding fanoutOneQueueBinding() {
        return BindingBuilder.bind(fanoutOneQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutTwoQueueBinding() {
        return BindingBuilder.bind(fanoutTwoQueue()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutThreeQueueBinding() {
        return BindingBuilder.bind(fanoutThreeQueue()).to(fanoutExchange());
    }
}
