package cn.santeamo.mq.rabbitmq.consumer.receiver;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听
 * @author santeamo
 */
@Slf4j
@Component
public class TopicReceiver {

    final String virtualHost = RabbitConst.Default.VIRTUAL_HOST;
    final String containerFactory = RabbitConst.Default.RABBIT_LISTENER_CONTAINER;
    final String topicOneQueue = RabbitConst.Topic.One.QUEUE;
    final String topicTwoQueue = RabbitConst.Topic.Two.QUEUE;

    @RabbitListener(containerFactory = containerFactory, queues = topicOneQueue)
    @RabbitHandler
    public void topicOneMsgReceiver(String msg) {
        log.info("[virtual-host = {}] [queue = {}] 收到消息 {} ", virtualHost, topicOneQueue, msg);
    }

    @RabbitListener(containerFactory = containerFactory, queues = topicTwoQueue)
    @RabbitHandler
    public void topicTwoMsgReceiver(String msg) {
        log.info("[virtual-host = {}] [queue = {}] 收到消息 {} ", virtualHost, topicTwoQueue, msg);
    }
}
