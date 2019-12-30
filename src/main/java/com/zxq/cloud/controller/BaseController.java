package com.zxq.cloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.zxq.cloud.config.RabbitMqConfig;
import com.zxq.cloud.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * @author zxq
 * @date 2019/12/12 17:15
 **/
@RestController
@Slf4j
public class BaseController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TaskService taskService;

    @GetMapping("/sendMsg")
    public String sendMsg(){
        String message = "send msg.....";
        rabbitTemplate.convertAndSend(RabbitMqConfig.MY_FIRST_EXCHANGE,RabbitMqConfig.MY_FIRST_ROUT_KEY,message);
        return "信息已发送";
    }

    @RequestMapping("/task")
    public Object task(){
        Future<JSONObject> future = taskService.taskTwo();
        JSONObject jsonObject = null;
        try {
            //这里会阻塞读取
            jsonObject = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return jsonObject;
    }
}
