package com.zxq.cloud.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
