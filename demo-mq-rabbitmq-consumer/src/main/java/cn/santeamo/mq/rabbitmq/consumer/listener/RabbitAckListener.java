package cn.santeamo.mq.rabbitmq.consumer.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author santeamo
 */
@Component
public class RabbitAckListener implements ChannelAwareMessageListener {

    /**
     * 消息处理
     * 重新入列这个确认模式要谨慎，可能会产生 消费-入列-消费-入列 这样的循环，会导致消息积压
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            String msg = new String(message.getBody());
            System.out.println("RabbitAckListener message ==> " + msg);
            // 第一个参数，deliveryTag 是当前消息到的 tag
            // 第二个参数，multiple 是指是否针对多条消息；如果是 true 则拒绝小于或等于当前 delivery tag 的消息；如果是 false 则只会拒绝当前 delivery tag 的消息
            // 第三个参数，requeue 是指是否重新入列；如果是 true 则拒绝的消息会重新入队；否则 会丢弃 成为 死信(dead-lettered)
            // channel.basicNack(deliveryTag, false, false);
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            // 拒绝消费当前消息，如果第二参数传入true，就是将数据重新丢回队列里，那么下次还会消费这消息；
            // 设置false，就是告诉服务器，我已经知道这条消息数据了，因为一些原因拒绝它，而且服务器也把这个消息丢掉就行。下次不想再消费这条消息了。
            // 使用拒绝后重新入列这个确认模式要谨慎，因为一般都是出现异常的时候，catch异常再拒绝入列，选择是否重入列。
            // 但是如果使用不当会导致一些每次都被你重入列的消息一直消费-入列-消费-入列这样循环，会导致消息积压。
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }
}
