package cn.santeamo.mq.rabbitmq.consumer.receiver;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author santeamo
 */
@Slf4j
@Component
public class FanoutReceiver {

    private final String queueOne = RabbitConst.Fanout.One.QUEUE;
    private final String queueTwo = RabbitConst.Fanout.Two.QUEUE;
    private final String queueThree = RabbitConst.Fanout.Three.QUEUE;

    @RabbitListener(containerFactory = RabbitConst.Default.RABBIT_LISTENER_CONTAINER, queues = queueOne)
    @RabbitHandler
    public void fanoutOneMsgReceiver(String msg) {
        log.info("[virtual-host = {}] [queue = {}] 收到消息 {} ", RabbitConst.Default.VIRTUAL_HOST, queueOne, msg);
    }

    @RabbitListener(containerFactory = RabbitConst.Default.RABBIT_LISTENER_CONTAINER, queues = queueTwo)
    @RabbitHandler
    public void fanoutTwoMsgReceiver(String msg) {
        log.info("[virtual-host = {}] [queue = {}] 收到消息 {} ", RabbitConst.Default.VIRTUAL_HOST, queueTwo, msg);
    }

    @RabbitListener(containerFactory = RabbitConst.Default.RABBIT_LISTENER_CONTAINER, queues = queueThree)
    @RabbitHandler
    public void fanoutThreeMsgReceiver(String msg) {
        log.info("[virtual-host = {}] [queue = {}] 收到消息 {} ", RabbitConst.Default.VIRTUAL_HOST, queueThree, msg);
    }
}
