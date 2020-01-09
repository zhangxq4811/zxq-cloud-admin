package com.zxq.cloud.mq;

import com.rabbitmq.client.Channel;
import com.zxq.cloud.config.RabbitMqConfig;
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
    public void processCpcSettleMessage(String json, Channel channel) {
        log.info(">>>>>>>接受到信息了<<<<<<");
        log.info("队列已接受到信息===》{},成功消费",json);
    }

    /**
     * @param deadLetterMsg
     */
    @RabbitListener(queues = RabbitMqConfig.DEAD_LETTER_QUEUE)
    public void processDeadLetterMessage(String deadLetterMsg) {
        log.info("死信队列已接受到信息===》{}",deadLetterMsg);
    }

    /**
     * @param redirectMsg
     */
    @RabbitListener(queues = RabbitMqConfig.REDIRECT_QUEUE)
    public void processRedirectMessage(String redirectMsg) {
        log.info("接收到由死信队列重定向过来的信息===》{}",redirectMsg);
    }

}
