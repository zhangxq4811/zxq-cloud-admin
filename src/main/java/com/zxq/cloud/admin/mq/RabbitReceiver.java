package com.zxq.cloud.admin.mq;

import com.zxq.cloud.admin.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zxq
 * @date 2019/12/12 15:59
 **/
@Component
@Slf4j
public class RabbitReceiver {

    /**
     * @param json
     */
    @RabbitListener(queues = RabbitMqConfig.MY_FIRST_QUEUE)
    public void processCpcSettleMessage(String json) {
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info("队列已接受到信息===》{}",json);
    }
}
