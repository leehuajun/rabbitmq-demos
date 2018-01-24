package com.sunjet.demo06;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: lhj
 * @create: 2017-11-11 13:47
 * @description: 说明
 */
@Slf4j
@Component
public class Receiver {

    @RabbitListener(queues = "first_to_settlement")
    public void receiveFirstToSettlement(String message) {
        // message可以理解为任何内容，这里存储单据id
        System.out.println("[ x first_to_settlement ]Receive: " + message);
    }

    @RabbitListener(queues = "freight_to_settlement")
    public void receiveFreightToSettlement(String message) {
        // message可以理解为任何内容，这里存储单据id
        System.out.println("[ x freight_to_settlement ]Receive : " + message);
    }
}
