package cn.cvtb.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: lhj
 * @create: 2017-11-26 13:04
 * @description: 说明
 */
public class Sender {

    private static final String QUEUE_NAME = "hello";

    @Test
    public void send() throws IOException, TimeoutException {

        // 建立连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.104.80.236");
        factory.setVirtualHost("test");
        factory.setUsername("test");
        factory.setPassword("test");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 设置了队列和消息的持久化之后，当broker服务重启的之后，消息依旧存在。
        // 单只设置队列持久化，重启之后消息会丢失；
        // 单只设置消息的持久化，重启之后队列消失，既而消息也丢失。单单设置消息持久化而不设置队列的持久化显得毫无意义。


        // 声明Queue
        // durable = true, 参数表示要持久化queue,将queue的持久化标识durable设置为true,
        // 则代表是一个持久的队列，那么在服务重启之后，也会存在，因为服务会把持久化的queue存放在硬盘上
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 发送消息
        String message = "Hello World!";
        // 设置消息的持久化
        // MessageProperties.MINIMAL_PERSISTENT_BASIC   表示消息需要持久化到硬盘
        // 可以查看 MessageProperties.MINIMAL_PERSISTENT_BASIC 的定义
        // deliveryMode=1代表不持久化，deliveryMode=2代表持久化
        channel.basicPublish("", QUEUE_NAME, MessageProperties.MINIMAL_PERSISTENT_BASIC, message.getBytes());
//        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("发送消息：" + message);

        connection.close();
    }

}
