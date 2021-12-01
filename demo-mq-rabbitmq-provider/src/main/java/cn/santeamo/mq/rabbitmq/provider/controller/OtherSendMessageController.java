package cn.santeamo.mq.rabbitmq.provider.controller;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

/**
 * 消息发送
 *
 * @author santeamo
 */
@RestController
@RequestMapping("/other")
public class OtherSendMessageController {

    private final RabbitTemplate rabbitTemplate;

    public OtherSendMessageController(@Qualifier(RabbitConst.Other.RABBIT_TEMPLATE) RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        // 将消息携带绑定键值：directRoutingKey 发送到交换机 directExchange
        rabbitTemplate.convertAndSend(
            RabbitConst.Direct.EXCHANGE,
            RabbitConst.Direct.ROUTING_KEY,
            getMessageMap("otherSendDirectMessage").toString());
        return "ok";
    }

    /**
     * 消息推送到server，但是在server里找不到交换机
     * <p>
     * 这种情况触发的是 ConfirmCallback 回调函数
     *
     * @return {@link String}
     */
    @GetMapping("/testNoExistExchange")
    public String testNoExistExchange() {
        rabbitTemplate.convertAndSend(
            "not-exist-exchange",
            RabbitConst.Direct.ROUTING_KEY,
            getMessageMap("testNoExistExchange").toString());
        return "ok";
    }

    /**
     * 消息推送到server，找到交换机了，但是没找到队列
     * 这种情况下，消息是推送成功到服务器了的，所以 ConfirmCallback 对消息确认情况是true；
     * 而在 ReturnCallback 回调函数的打印参数里面可以看到，消息是推送到了交换机成功了，但是在路由分发给队列的时候，找不到队列，所以报了错误 NO_ROUTE
     * <p>
     * 这种情况触发的是 ConfirmCallback 和 ReturnCallback 两个回调函数。
     *
     * @return {@link String}
     */
    @GetMapping("/testNoExistRoute")
    public String testNoExistRoute() {
        rabbitTemplate.convertAndSend(
            RabbitConst.NonRoutingKeyDirect.EXCHANGE,
            RabbitConst.Direct.ROUTING_KEY,
            getMessageMap("testNoExistRoute").toString());
        return "ok";
    }

    private HashMap<String, Object> getMessageMap(String from) {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "hello! message from " + from;
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HashMap<String, Object> map = new HashMap<>(8);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        return map;
    }
}
