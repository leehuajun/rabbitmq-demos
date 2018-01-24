package com.sunjet.demospringbootrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoSpringbootRabbitmqApplicationTests {

	@Autowired
	private HelloSender helloSender;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSend() throws InterruptedException {
		for(int i=1;i<=10;i++) {
			helloSender.send("hello world!");
		}
	}

}
