package com.zxq.cloud.admin.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author zxq
 * @date 2019/12/27 14:47
 **/
@Service
@Slf4j
public class TaskService {

    @Async
    public void taskOne(){
        log.info("无返回值异步任务");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public Future<JSONObject>  taskTwo(){
        log.info("有返回值异步任务");
        JSONObject result = new JSONObject();
        result.put("message","测试返回值");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(result);
    }

    @Async
    public String taskThree() {
        log.info("出现异常的异步任务");
        int i = 2 / 0;
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "error";
    }

}
