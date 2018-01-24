package com.sunjet.demospringbootrabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: lhj
 * @create: 2017-11-27 12:22
 * @description: 说明
 */
@Slf4j
@Component
public class HelloReceiver {

    @RabbitListener(queues = "springboot-hello2")
    public String process(String message) {
        log.info(String.format("receive message: %s", message));
        return message;
    }
}
