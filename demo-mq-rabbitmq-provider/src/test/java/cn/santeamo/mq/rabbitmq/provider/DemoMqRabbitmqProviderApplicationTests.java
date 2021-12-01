package cn.santeamo.mq.rabbitmq.provider;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoMqRabbitmqProviderApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier(RabbitConst.Other.RABBIT_TEMPLATE)
    private RabbitTemplate otherRabbitTemplate;

    /**
     * 测试直接模式发送
     */
    @Test
    public void sendDirect() {
        String queue = RabbitConst.Direct.QUEUE;
        rabbitTemplate.convertAndSend(queue, queue + " message");
    }

    /**
     * 测试直接模式发送
     */
    @Test
    public void otherSendDirect() {
        String queue = RabbitConst.Direct.QUEUE;
        otherRabbitTemplate.convertAndSend(queue, queue + " message");
    }

    /**
     * 测试直接模式发送
     */
    @Test
    public void sendTopicOne() {
        String queue = RabbitConst.Topic.One.QUEUE;
        rabbitTemplate.convertAndSend(queue, queue + " message");
    }

    /**
     * 测试直接模式发送
     */
    @Test
    public void sendTopicTwo() {
        String queue = RabbitConst.Topic.Two.QUEUE;
        rabbitTemplate.convertAndSend(queue, queue + " message");
    }

    /**
     * 测试直接模式发送
     */
    @Test
    public void sendFanout() {
        String exchange = RabbitConst.Fanout.EXCHANGE;
        rabbitTemplate.convertAndSend(exchange, null, exchange + " message");
    }

}
