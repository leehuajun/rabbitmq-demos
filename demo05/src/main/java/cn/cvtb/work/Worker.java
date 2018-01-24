package cn.cvtb.work;

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
public class Worker {

    private static final String QUEUE_NAME = "work-queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 建立连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.101");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明Queue
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);

        // 创建Consumer
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);
        System.err.println("等待任务中");

        // 接受消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            execute(message);
        }
    }

    private static void execute(String task) throws InterruptedException {
        System.out.println("执行任务: " + task);
        Thread.sleep(1000);
    }
}
