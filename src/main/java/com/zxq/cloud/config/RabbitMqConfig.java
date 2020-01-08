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
        return new DirectExchange(MY_FIRST_EXCHANGE);
    }

    @Bean
    public Queue myFirstQueue(){
        return new Queue(MY_FIRST_QUEUE);
    }

    @Bean
    public Binding myFirstBinding(Exchange myFirstExchange,Queue myFirstQueue){
        return BindingBuilder.bind(myFirstQueue).to(myFirstExchange).with(MY_FIRST_ROUT_KEY).noargs();
    }

    /**--------------------------死信队列配置-----------------**/

    @Bean("deadLetterExchange")
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange("DL_EXCHANGE").durable(true).build();
    }

    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<>(2);
//       x-dead-letter-exchange    声明  死信交换机
        args.put("x-dead-letter-exchange", "DL_EXCHANGE");
//       x-dead-letter-routing-key    声明 死信路由键
        args.put("x-dead-letter-routing-key", "KEY_R");
        return QueueBuilder.durable("DL_QUEUE").withArguments(args).build();
    }

    @Bean("redirectQueue")
    public Queue redirectQueue() {
        return QueueBuilder.durable("REDIRECT_QUEUE").build();
    }

    /**
     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return new Binding("DL_QUEUE", Binding.DestinationType.QUEUE, "DL_EXCHANGE", "DL_KEY", null);

    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding("REDIRECT_QUEUE", Binding.DestinationType.QUEUE, "DL_EXCHANGE", "KEY_R", null);
    }
}
