package com.sunjet.demospringbootrabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @author: lhj
 * @create: 2017-11-27 12:25
 * @description: 说明
 */
@Slf4j
@Component
public class HelloSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    public void send(String message) throws InterruptedException {
//        while (true) {
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            System.out.println("开始发送消息 : " + message.toLowerCase());
            rabbitTemplate.convertAndSend("", "mule-queue", message, correlationId);
//            rabbitTemplate.convertAndSend("helloExchange", "key.1", message, correlationId);
//            String response = rabbitTemplate.convertSendAndReceive("helloExchange", "key.1", message, correlationId).toString();
            System.out.println("结束发送消息 : " + message.toLowerCase());
//            System.out.println("消费者响应 : " + response + " 消息处理完成");
//            Thread.sleep(2000);
//        }
//        log.info(String.format("发送消息: %s", message));
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息发送成功:" + correlationData);
        } else {
            System.out.println("消息发送失败:" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println(message.getMessageProperties().getCorrelationIdString() + " 发送失败");
    }
}
