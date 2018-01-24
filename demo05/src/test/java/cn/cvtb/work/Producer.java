package cn.cvtb.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: lhj
 * @create: 2017-11-26 13:31
 * @description: 说明
 */
public class Producer {
    private static final String QUEUE_NAME="work-queue";

    @Test
    public void send() throws IOException, TimeoutException {

        // 建立连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.56.101");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明Queue
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        // 发送消息

        for(int i=1;i<=10;i++){
            String message = "Task-" + i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送任务：" + message);
        }

        connection.close();
    }
}
