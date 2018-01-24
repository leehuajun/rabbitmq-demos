package cn.cvtb.receiver;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import static cn.cvtb.receiver.Constants.*;

/**
 * Hello world!
 */
public class Worker {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //区分不同工作进程的输出
        final int hashCode = Worker.class.hashCode();
        System.out.println("Hello World!");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_SERVER);
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUAL_HOST);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        boolean durable = true; //设置消息持久化  RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
        //声明队列
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        boolean ack = false; //打开应答机制
        //公平转发  设置最大服务转发消息数量    只有在消费者空闲的时候会发送下一条信息。
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

//        QueueingConsumer consumer = new QueueingConsumer(channel);
        final int[] count = {0};
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
                String message = new String(body, "UTF-8");
                System.out.println(hashCode + " Received Message：'" + message + "'");
                try {
//                    System.out.println("开始处理，序号：" + envelope.getDeliveryTag());
//                    doWork(message);
                    count[0]++;
                    System.out.println(message);
                } catch (Exception e) {
                    System.out.println("处理失败，序号：" + envelope.getDeliveryTag());
                    e.printStackTrace();
                } finally {
//                    System.out.println("处理成功，序号：" + envelope.getDeliveryTag());

//                    System.out.println(" [x] Done");
                    //处理完成后，发送应答
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

                System.out.println("共处理消息数量：" + count[0]);
//                System.out.println(hashCode + " Received Done");
//                channel.basicAck(envelope.getDeliveryTag(), false);
//            //发送应答
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };


        /**
         * ack= true: Round-robin 转发   消费者被杀死，消息会丢失
         * ack=false:消息应答 ，为了保证消息永远不会丢失，RabbitMQ支持消息应答（message acknowledgments）。
         * 消费者发送应答给RabbitMQ，告诉它信息已经被接收和处理，然后RabbitMQ可以自由的进行信息删除。
         * 如果消费者被杀死而没有发送应答，RabbitMQ会认为该信息没有被完全的处理，然后将会重新转发给别的消费者。
         * 通过这种方式，你可以确认信息不会被丢失，即使消者偶尔被杀死。
         * 消费者需要耗费特别特别长的时间是允许的。
         *
         */



//        // 指定消费队列
        channel.basicConsume(QUEUE_NAME, ack, consumer);
//
//


//
//        while (true) {
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            String message = new String(delivery.getBody());
//
//            System.out.println(hashCode + " Received Message：'" + message + "'");
//            doWork(message);
//            System.out.println(hashCode + " Received Done");
//            //发送应答
//            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
//
//        }

    }

    /**
     * 每个点耗时1s
     *
     * @param task
     * @throws InterruptedException
     */
    private final static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
