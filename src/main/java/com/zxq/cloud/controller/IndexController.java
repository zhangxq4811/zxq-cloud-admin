package com.zxq.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zxq.cloud.service.RedisService;

/**
 * @author zxq
 * @date 2019/12/27 17:34
 **/
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("get")
    public Object get() {
        return redisService.get("user");
    }
}
