package com.wing.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitSenderTest {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private Processor processor;

    @Test
    public void send() {
        String sendMsg = "hello1 " + new Date();
        this.rabbitTemplate.convertAndSend("myQueue", sendMsg);
    }

    @Test
    public void sendDigital() {
        String sendMsg = "hello1 " + new Date();
        this.rabbitTemplate.convertAndSend( "orderExchange","digital", sendMsg);
    }

    @Test
    public void sendDigitalStream() {
        String sendMsg = "hello1 " + new Date();
        this.processor.output().send(MessageBuilder.withPayload(sendMsg).build());
    }

}
