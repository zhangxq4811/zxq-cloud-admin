package com.zxq.cloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明：〈该类初始化创建队列、转发器，并把队列绑定到转发器〉
 *
 * @author zxq
 * @date 2019/12/12 15:59
 **/
@Configuration
@Slf4j
public class RabbitMqConfig {

    /**
     * 交换器
     */
    public static final String MY_FIRST_EXCHANGE = "com.zxq.cloud.admin.my_first_exchange";

    /**
     * 队列
     */
    public static final String MY_FIRST_QUEUE = "com.zxq.cloud.admin.my_first_queue";

    /**
     * 路由
     */
    public static final String MY_FIRST_ROUT_KEY = "com.zxq.cloud.admin.my_first_rout_key";

    @Bean
    public Exchange myFirstExchange(){
        return ExchangeBuilder.directExchange(MY_FIRST_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue myFirstQueue(){
        return QueueBuilder.durable(MY_FIRST_QUEUE).build();
    }

    @Bean
    public Binding myFirstBinding(Exchange myFirstExchange,Queue myFirstQueue){
        return BindingBuilder.bind(myFirstQueue).to(myFirstExchange).with(MY_FIRST_ROUT_KEY).noargs();
    }

    /**--------------------------死信队列配置-----------------**/

    public static final String DEAD_LETTER_EXCHANGE = "DL_EXCHANGE";
    public static final String DEAD_LETTER_QUEUE = "DL_QUEUE";
    public static final String DEAD_LETTER_ROUTING_KEY = "DL_KEY";
    public static final String DEAD_LETTER_REDIRECT_ROUTING_KEY = "KEY_R";
    public static final String REDIRECT_QUEUE = "REDIRECT_QUEUE";

    /**
     * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
     */
    @Bean("deadLetterExchange")
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(DEAD_LETTER_EXCHANGE).durable(true).build();
    }

    /**
     * 死信队列
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<>(2);
//       x-dead-letter-exchange    声明  死信队列Exchange
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
//       x-dead-letter-routing-key    声明 死信队列抛出异常重定向队列的routingKey(TKEY_R)
        args.put("x-dead-letter-routing-key", DEAD_LETTER_REDIRECT_ROUTING_KEY);
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).withArguments(args).build();
    }

    /**
     * 死信队列绑定到死信交换器上.
     *
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding(Exchange deadLetterExchange, Queue deadLetterQueue) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DEAD_LETTER_ROUTING_KEY).noargs();
    }

    @Bean("redirectQueue")
    public Queue redirectQueue() {
        return QueueBuilder.durable(REDIRECT_QUEUE).build();
    }

    /**
     * 将重定向队列通过routingKey(TKEY_R)绑定到死信队列的Exchange上
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding(REDIRECT_QUEUE, Binding.DestinationType.QUEUE, DEAD_LETTER_EXCHANGE, DEAD_LETTER_REDIRECT_ROUTING_KEY, null);
    }

}

