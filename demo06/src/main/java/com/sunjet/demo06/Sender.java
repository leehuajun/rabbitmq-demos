package com.sunjet.demo06;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class Sender {

    @Autowired
    private ConfirmCallback confirmCallback;

    @Autowired
    private ReturnCallback returnCallback;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    public Sender(RabbitTemplate rabbitTemplate){
//        this.rabbitTemplate = rabbitTemplate;
//        this.rabbitTemplate.setConfirmCallback(this.confirmCallback);
//    }

    @PostConstruct
    public void init() {
        this.rabbitTemplate.setConfirmCallback(confirmCallback);
        this.rabbitTemplate.setReturnCallback(returnCallback);
    }

    public void send(String message, String exchange, String routingKey) throws InterruptedException {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("发送消息 : " + message.toLowerCase() + "  correlationId:" + correlationId);
//        rabbitTemplate.convertSendAndReceive(exchange, routingKey, message, correlationId);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationId);
//        System.out.println("结束发送消息 : " + message.toLowerCase());

    }
}