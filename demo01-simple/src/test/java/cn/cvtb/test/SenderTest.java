package cn.cvtb.test;

import cn.cvtb.receiver.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static cn.cvtb.receiver.Constants.*;

/**
 * @author: lhj
 * @create: 2017-01-13 17:37
 * @description: 说明
 */
public class SenderTest {
    @Test
    public void send() throws IOException, TimeoutException {
        System.out.println( "Hello World!" );
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_SERVER);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setPort(AMQP.PROTOCOL.PORT);


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for(int i=1;i<10;i++) {
            User user = new User(i,"user"+i,"address"+i);
            ObjectMapper mapper = new ObjectMapper();

            String message = mapper.writeValueAsString(user);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Sent Message: '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}
