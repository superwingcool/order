package com.wing.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageReceiver {

    //@RabbitListener(queuesToDeclare = @Queue("myTestQueue"))
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String hello) {
        log.info(" ==========Receiver {} ========= ", hello);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("digitalQueue"),
            exchange = @Exchange("orderExchange"),
            key = "digital"
    ))
    public void processDigital(String hello) {
        log.info(" ========== processDigital Receiver {} ========= ", hello);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitQueue"),
            exchange = @Exchange("orderExchange"),
            key = "fruit"
    ))
    public void processFruit(String hello) {
        log.info(" ========== processFruit Receiver {} ========= ", hello);
    }



}