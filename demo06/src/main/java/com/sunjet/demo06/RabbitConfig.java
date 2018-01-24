package com.sunjet.demo06;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lhj
 * @create: 2017-11-27 16:25
 * @description: 说明
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue firstToSettlementQueue(){
        return new Queue("first_to_settlement",true);
    }

    @Bean
    public Queue freightToSettlementQueue(){
        return new Queue("freight_to_settlement",true);
    }

    //声明交互器
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    //绑定
    @Bean
    public Binding bindingFirstToSettlement() {
        return BindingBuilder.bind(firstToSettlementQueue()).to(topicExchange()).with("key.first_to_settlement");
    }

    //绑定
    @Bean
    public Binding bindingFreightToSettlement() {
        return BindingBuilder.bind(freightToSettlementQueue()).to(topicExchange()).with("key.freight_to_settlement");
    }

    //绑定
//    @Bean
//    public Binding bindingAll() {
//        return BindingBuilder.bind(freightToSettlementQueue()).to(topicExchange()).with("key.*");
//    }
}
