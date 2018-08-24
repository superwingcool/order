package com.wing.order.service;

import com.wing.order.entity.ProductInfo;
import com.wing.order.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StreamReceiver {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @StreamListener(Processor.INPUT)
    //@SendTo(//other quene)
    public void handle(String value) {
        List<ProductInfo> products = JsonMapper.jsonToObject(value, List.class, ProductInfo.class);
        for (ProductInfo p: products) {
            log.info("===============" + p.getProductId() + "," + p.getProductStock());
            stringRedisTemplate.opsForValue().set("spring-cloud-redis" + p.getProductId(), String.valueOf(p.getProductStock()));
        }
    }
}