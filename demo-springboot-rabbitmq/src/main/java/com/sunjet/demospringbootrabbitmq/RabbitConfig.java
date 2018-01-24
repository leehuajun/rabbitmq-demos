package com.sunjet.demospringbootrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: lhj
 * @create: 2017-11-27 14:41
 * @description: 说明
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue(){
        return new Queue("springboot-hello");
    }
    @Bean
    public Queue queue2(){
        return new Queue("springboot-hello2");
    }

    //声明交互器
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("helloExchange");
    }

    //绑定
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("key.1");
    }

    //绑定
    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("key.2");
    }
}
