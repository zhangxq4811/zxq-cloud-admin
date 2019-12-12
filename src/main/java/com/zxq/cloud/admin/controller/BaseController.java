package com.zxq.cloud.admin.controller;

import com.zxq.cloud.admin.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxq
 * @date 2019/12/12 17:15
 **/
@RestController
@Slf4j
public class BaseController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg")
    public String sendMsg(){
        String message = "send msg.....";
        rabbitTemplate.convertAndSend(RabbitMqConfig.MY_FIRST_EXCHANGE,RabbitMqConfig.MY_FIRST_ROUT_KEY,message);
        return "信息已发送";
    }
}
