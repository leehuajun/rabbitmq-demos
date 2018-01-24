package cn.cvtb;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import static cn.cvtb.Constants.*;

/**
 * @author: lhj
 * @create: 2017-01-13 17:37
 * @description: 发送日志
 */
public class SenderTest {
    @Test
    public void send() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_SERVER);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        factory.setPort(AMQP.PROTOCOL.PORT);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String message = new Date().toLocaleString() + " : log something";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
