package cn.cvtb;

import com.rabbitmq.client.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import static cn.cvtb.Constants.*;

/**
 * @author: lhj
 * @create: 2017-07-28 15:14
 * @description: 接受信息并写文件类代码
 */
public class ReceiveLogsToFile {
    private static final String queueName = "queue-file";
    /**
     * 主函数
     * @param args
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_SERVER);
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("[*] Waiting for message. To exist press CTRL+C");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            privateToFile(message);
        }
    }

    /**
     * 测试一下
     * @param message
     */
    private static void privateToFile(String message) {
        try {
            String dir = ReceiveLogsToFile.class.getClassLoader().getResource("").getPath();
            String logFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File file = new File(dir,logFileName + ".txt");
            FileOutputStream fos = new FileOutputStream(file,true);
            fos.write((message + "\r\n").getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
