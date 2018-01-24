package com.sunjet.demo06;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * @author: lhj
 * @create: 2017-11-27 17:55
 * @description: 说明
 */
@Slf4j
@Component
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送成功:" + correlationData);
        } else {
            log.error("消息发送失败:" + cause);
        }
    }
}
