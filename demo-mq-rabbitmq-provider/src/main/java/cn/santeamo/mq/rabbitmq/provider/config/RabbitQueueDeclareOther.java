package cn.santeamo.mq.rabbitmq.provider.config;

import cn.santeamo.mq.rabbitmq.provider.constants.RabbitConst;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * rabbitmq 队列声明
 * @author santeamo
 */
//@Configuration
public class RabbitQueueDeclareOther {

    private final ConnectionFactory connectionFactory;

    public RabbitQueueDeclareOther(@Qualifier(RabbitConst.Other.CONNECTION_FACTORY) ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public String queueDeclareOther() {
        try {
            connectionFactory.createConnection().createChannel(false)
                .queueDeclare(RabbitConst.Direct.QUEUE, true, false, false, null);
        }catch (IOException e){
            e.printStackTrace();
        }
        return RabbitConst.Direct.QUEUE;
    }

    @Bean
    public String exchangeDeclareOther(){
        try {
            connectionFactory.createConnection().createChannel(false)
                .exchangeDeclare(RabbitConst.Direct.EXCHANGE, ExchangeTypes.DIRECT, true);
        }catch (IOException e){
            e.printStackTrace();
        }
        return RabbitConst.Direct.EXCHANGE;
    }

    @Bean
    public String queueBindOther(){
        try {
            connectionFactory.createConnection().createChannel(false)
                .queueBind(RabbitConst.Direct.QUEUE, RabbitConst.Direct.EXCHANGE, RabbitConst.Direct.ROUTING_KEY);
        }catch (IOException e){
            e.printStackTrace();
        }
        return RabbitConst.Direct.ROUTING_KEY;
    }
}
