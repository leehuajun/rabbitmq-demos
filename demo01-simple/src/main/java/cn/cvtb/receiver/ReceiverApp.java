package cn.cvtb.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static cn.cvtb.receiver.Constants.*;

/**
 * Hello world!
 */
public class ReceiverApp {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("Hello World!");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_SERVER);
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUAL_HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//        channel.basicConsume(QUEUE_NAME,true,consumer);
//        while(true){
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            String message = new String(delivery.getBody());
//            ObjectMapper mapper = new ObjectMapper();
//            User user = mapper.readValue(message, User.class);
//            System.out.println("Received Message:'" + user.toString() + "'");
//        }

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                ObjectMapper mapper = new ObjectMapper();
                User user = mapper.readValue(message, User.class);
                System.out.println("Received Message:'" + user.toString() + "'");

            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
