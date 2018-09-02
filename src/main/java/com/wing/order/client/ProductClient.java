package com.wing.order.client;

import com.wing.order.vo.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("product")
public interface ProductClient {

//    @GetMapping("/msg")
//    String getMsg();

    @PutMapping("/products/decreaseStock")
    void decreaseStock(@RequestBody List<CartDTO> carts);
}
