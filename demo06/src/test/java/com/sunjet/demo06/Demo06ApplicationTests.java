package com.sunjet.demo06;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo06ApplicationTests {

    @Autowired
    private Sender sender;

    @Test
    public void testSend() throws InterruptedException {
        for (int i = 1; i <= 1000000; i++) {
            if (i % 2 == 0) {
                sender.send("Message " + i, "topicExchange", "key.first_to_settlement");
            } else {
                sender.send("Message " + i, "topicExchange", "key.freight_to_settlement");
            }
        }
    }
}
