package com.rabbitmq.service;

import com.rabbitmq.bean.TestBean;
import com.rabbitmq.mapper.TestMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by liwk
 * Date:2018/8/6 9:38
 */
@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    /**
     * 创建交换器 队列 绑定 存放数据
     * @param id
     * @return
     */
    public TestBean getBeanId(Integer id){
        Properties properties = amqpAdmin.getQueueProperties("testmq");
        if(properties == null){
            //创建单播交换器
            amqpAdmin.declareExchange(new DirectExchange("exChange.testmq"));
            //创建队列 1.队列名称 2.是否持久化
            amqpAdmin.declareQueue(new Queue("testmq", true));
            //交换器和队列绑定 1.目的地(队列名称) 2.目的地类型 3.交换器 4.路由键
            amqpAdmin.declareBinding(new Binding("testmq", Binding.DestinationType.QUEUE, "exChange.testmq", "testmq", null));
        }
        TestBean testBean = testMapper.getBeanId(id);
        rabbitTemplate.convertAndSend("exChange.testmq", "testmq", testBean);
        return testBean;
    }

    /**
     * 获得队列中对应的内容
     * @return
     */
    public TestBean receiveAndConvert(){
        return (TestBean)rabbitTemplate.receiveAndConvert("testmq");
    }

    /**
     * 监听testmq队列里的内容
     * @param testBean
     */
    @RabbitListener
    public void rabbitListener(TestBean testBean){
        System.out.println(testBean);
    }

}
