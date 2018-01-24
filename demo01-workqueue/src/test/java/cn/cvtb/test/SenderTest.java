package cn.cvtb.test;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static cn.cvtb.receiver.Constants.*;

/**
 * @author: lhj
 * @create: 2017-01-13 17:37
 * @description: 说明
 */
public class SenderTest {
    private static final Object lock = new Object();
    private static List<String> ips = Arrays.asList("192.168.56.181", "192.168.56.182", "192.168.56.183");
    private static int pos = 0;

    @Test
    public void send() throws IOException, TimeoutException, InterruptedException {
        System.out.println("Hello World!");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_SERVER);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setVirtualHost(VIRTUAL_HOST);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        boolean durable = true; //设置消息持久化  RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
        //声明队列
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        // 启用信道的消息发送到达确认功能
        channel.confirmSelect();
        //发送10条消息，依次在消息后面附加1-10个点
        for (int i = 5; i > 0; i--) {
            String dots = "";
            for (int j = 0; j <= i; j++) {
                dots += ".";
            }
            String message = "helloworld" + dots + dots.length();
            //MessageProperties.PERSISTENT_TEXT_PLAIN 标识我们的信息为持久化的
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            // 如果没有返回送达确认，表示发送失败
            if (!channel.waitForConfirms()) {
                System.out.println("发送失败");
            } else {  // 表示发送送达确认成功。
                System.out.println("发送成功");
                Thread.sleep(1000);
            }
            System.out.println("Sent Message：'" + message + "'");
        }
        channel.close();
        connection.close();
    }

    @Test
    public void RoundRobinSendTest() throws IOException, TimeoutException, InterruptedException {


//        System.out.println("Hello World!");
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost(ip);
//        factory.setUsername(USER);
//        factory.setPassword(PASSWORD);
//        factory.setPort(AMQP.PROTOCOL.PORT);
//        factory.setVirtualHost(VIRTUAL_HOST);
//
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//        boolean durable = true; //设置消息持久化  RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
//        //声明队列
//        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
//        // 启用信道的消息发送到达确认功能
//        channel.confirmSelect();
        //发送10条消息，依次在消息后面附加1-10个点
        for (int i = 10; i > 0; i--) {

            String ip = null;
            synchronized (lock) {
                ip = ips.get(pos);
                if (++pos >= ips.size()) {
                    pos = 0;
                }
            }

            System.out.println("消息发送到IP:" + ip);
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(ip);
            factory.setUsername(USER);
            factory.setPassword(PASSWORD);
            factory.setPort(AMQP.PROTOCOL.PORT);
            factory.setVirtualHost(VIRTUAL_HOST);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            boolean durable = true; //设置消息持久化  RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
            //声明队列
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            // 启用信道的消息发送到达确认功能
            channel.confirmSelect();

            String dots = "";
            for (int j = 0; j <= i; j++) {
                dots += ".";
            }
            String message = "helloworld" + dots + dots.length();
            //MessageProperties.PERSISTENT_TEXT_PLAIN 标识我们的信息为持久化的
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            // 如果没有返回送达确认，表示发送失败
            if (!channel.waitForConfirms()) {
                System.out.println("发送失败");
            } else {  // 表示发送送达确认成功。
                System.out.println("发送成功");
                Thread.sleep(1000);
            }
            System.out.println("Sent Message：'" + message + "'");
            channel.close();
            connection.close();
        }
    }

    @Test
    public void sendHaproxy() throws IOException, TimeoutException, InterruptedException {
        System.out.println("Hello World!");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_SERVER);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        factory.setPort(5672);
        factory.setVirtualHost(VIRTUAL_HOST);


        //发送10条消息，依次在消息后面附加1-10个点
        for (int i = 10000; i > 0; i--) {
            boolean isConn = false;
            Connection connection = null;
            Channel channel = null;
            while (!isConn) {
                try {
                    connection = factory.newConnection();
                    channel = connection.createChannel();
                    boolean durable = true; //设置消息持久化  RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
                    //声明队列
                    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
                    isConn = true;
                } catch (Exception e) {

                }
            }
//            Connection connection = factory.newConnection();
//            Channel channel = connection.createChannel();
//            boolean durable = true; //设置消息持久化  RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
//            //声明队列
//            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            // 启用信道的消息发送到达确认功能
            channel.confirmSelect();
//            String dots = "";
//            for (int j = 0; j <= i; j++) {
//                dots += ".";
//            }
//            String message = "[" + i + "]" + " helloworld" + dots + dots.length();
            String message = "[" + i + "]" + " helloworld";
            //MessageProperties.PERSISTENT_TEXT_PLAIN 标识我们的信息为持久化的
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            // 如果没有返回送达确认，表示发送失败
            if (!channel.waitForConfirms()) {
                System.out.println("发送失败");
            } else {  // 表示发送送达确认成功。
                System.out.println("发送成功");
//                Thread.sleep(1000);
            }
            System.out.println("Sent Message：'" + message + "'");
            channel.close();
            connection.close();
        }

    }
}
