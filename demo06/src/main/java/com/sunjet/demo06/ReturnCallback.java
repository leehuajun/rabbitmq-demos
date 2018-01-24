package com.sunjet.demo06;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: lhj
 * @create: 2017-11-27 17:55
 * @description: 说明
 */
@Slf4j
@Component
public class ReturnCallback implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error(message.getMessageProperties().getCorrelationIdString() + " 发送失败");
    }
}
