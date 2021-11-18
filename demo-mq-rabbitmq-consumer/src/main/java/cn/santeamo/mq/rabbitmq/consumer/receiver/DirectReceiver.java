package cn.santeamo.mq.rabbitmq.consumer.receiver;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听
 * @author santeamo
 */
@Component
public class DirectReceiver {

    @RabbitListener(queues = RabbitConst.Direct.QUEUE)
    @RabbitHandler
    public void directMsgReceiver(String msg, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("收到消息，" + msg);
            // 第一个参数，deliveryTag 是当前消息到的 tag
            // 第二个参数，multiple 是指是否针对多条消息；如果是 true 则拒绝小于或等于当前 delivery tag 的消息；如果是 false 则只会拒绝当前 delivery tag 的消息
            // 第三个参数，requeue 是指是否重新入列；如果是 true 则拒绝的消息会重新入队；否则 会丢弃 成为 死信(dead-lettered)
            // channel.basicNack(deliveryTag, false, false);
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                channel.basicRecover();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 配置同样的监听队列，两个轮询对队列进行消费，没有重复消费
     * @param msg 消息
     */
    @RabbitListener(queues = RabbitConst.Direct.QUEUE)
    @RabbitHandler
    public void directMsgReceiverNew(String msg) {
        System.out.println("New 收到消息，" + msg);
    }
}
