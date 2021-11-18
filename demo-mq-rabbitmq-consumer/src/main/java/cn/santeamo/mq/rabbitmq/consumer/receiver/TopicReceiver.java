package cn.santeamo.mq.rabbitmq.consumer.receiver;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听
 * @author santeamo
 */
@Component
public class TopicReceiver {

    final String topicOneQueue = RabbitConst.Topic.One.QUEUE;
    final String topicTwoQueue = RabbitConst.Topic.Two.QUEUE;

    @RabbitListener(queues = topicOneQueue)
    @RabbitHandler
    public void topicOneMsgReceiver(String msg) {
        System.out.println("监听 " + topicOneQueue + " 收到消息，" + msg);
    }

    @RabbitListener(queues = topicTwoQueue)
    @RabbitHandler
    public void topicTwoMsgReceiver(String msg) {
        System.out.println("监听 " + topicTwoQueue + " 收到消息，" + msg);
    }
}
