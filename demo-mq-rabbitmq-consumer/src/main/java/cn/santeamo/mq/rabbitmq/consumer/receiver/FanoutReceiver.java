package cn.santeamo.mq.rabbitmq.consumer.receiver;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiver {

    private final String queueOne = RabbitConst.Fanout.One.QUEUE;
    private final String queueTwo = RabbitConst.Fanout.Two.QUEUE;
    private final String queueThree = RabbitConst.Fanout.Three.QUEUE;

    @RabbitListener(queues = queueOne)
    @RabbitHandler
    public void fanoutOneMsgReceiver(String msg) {
        System.out.println("监听 " + queueOne + " 收到消息，" + msg);
    }

    @RabbitListener(queues = queueTwo)
    @RabbitHandler
    public void fanoutTwoMsgReceiver(String msg) {
        System.out.println("监听 " + queueTwo + " 收到消息，" + msg);
    }

    @RabbitListener(queues = queueThree)
    @RabbitHandler
    public void fanoutThreeMsgReceiver(String msg) {
        System.out.println("监听 " + queueThree + " 收到消息，" + msg);
    }
}
