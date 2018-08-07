package com.rabbitmq;

import com.rabbitmq.bean.TestBean;
import com.rabbitmq.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

	@Autowired
	private TestService testService;
	@Test
	public void contextLoads() {
		TestBean testBean = testService.getBeanId(1);
		System.out.println(testBean);
	}

    @Test
	public void receiveAndConvert(){
	    TestBean testBean = testService.receiveAndConvert();
        System.out.println(testBean);
    }

}
