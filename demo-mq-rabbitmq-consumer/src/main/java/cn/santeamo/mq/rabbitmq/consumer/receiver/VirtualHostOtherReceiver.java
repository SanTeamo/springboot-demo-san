package cn.santeamo.mq.rabbitmq.consumer.receiver;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * virtual-host = "other" 消息监听
 * @author santeamo
 */
@Slf4j
@Component
public class VirtualHostOtherReceiver {

    @RabbitListener(containerFactory = RabbitConst.Other.RABBIT_LISTENER_CONTAINER, queues = RabbitConst.Direct.QUEUE)
    @RabbitHandler
    public void directMsgReceiver(String msg, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[virtual-host = {}] [queue = {}] 收到消息 {} ", RabbitConst.Other.VIRTUAL_HOST, RabbitConst.Direct.QUEUE, msg);
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

}
