package cn.santeamo.mq.rabbitmq.provider.constants;

/**
 * @author user
 */
public interface RabbitConst {
    interface Direct {
        String QUEUE = "directQueue";
        String EXCHANGE = "directExchange";
        String ROUTING_KEY = "directRoutingKey";
    }
    interface NonRoutingKeyDirect {
        String EXCHANGE = "nonRoutingKeyDirectExchange";
    }
    interface Topic {
        interface One {
            String QUEUE = "topic.one";
            String ROUTING_KEY = "topic.one";
        }
        interface Two {
            String QUEUE = "topic.two";
            String ROUTING_KEY = "topic.two";
        }
        String EXCHANGE = "topicExchange";
        interface RoutingKey {
            String ALL = "topic.#";
        }
    }
    interface Fanout {
        interface One {
            String QUEUE = "fanout.one";
        }
        interface Two {
            String QUEUE = "fanout.two";
        }
        interface Three {
            String QUEUE = "fanout.three";
        }
        String EXCHANGE = "fanoutExchange";
    }
}
