package com.wing.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
    @RequestMapping("/hystrix-test")
//默认的callback方法设置
@DefaultProperties(defaultFallback = "defaultCallBack")
public class HystrixController {

//    @HystrixCommand(commandProperties = {
//            // hystrix超时时间设置
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//    })
    // hystrix的熔断设置
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            // hystrix打开熔断
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            // 这个两个属性的意思是在10次请求中如果有7次请求触发服务降级，则会打开熔断。
            // 10=每10次请求
            // 60=这10次请求中超过60%触发降级服务
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
            // （时钟设置）hystrix打开熔断后，10秒后将进入half open状态，尝试一次真正的请求
            // 如果成功进入close状态
            // 如果失败继续在open状态
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")

    })
    @GetMapping("/{ids}")
    public String list(@PathVariable("ids") String ids, @RequestParam("num") int num) {
        if(num % 2 == 0) {
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:9201/products/" + ids, String.class);
//        throw new RuntimeException("111");
    }

    public String fallback(String ids) {
        return "太拥挤，请稍后重试";
    }

    public String defaultCallBack() {
        return "默认太拥挤，请稍后重试";
    }
}
