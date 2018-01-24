package cn.cvtb.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: lhj
 * @create: 2017-11-26 13:03
 * @description: 说明
 */
public class Receiver {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 建立连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.104.80.236");
        factory.setVirtualHost("test");
        factory.setUsername("test");
        factory.setPassword("test");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明Queue
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 创建Consumer
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        // 接受消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("接受到消息: " + message);
        }
    }
}
