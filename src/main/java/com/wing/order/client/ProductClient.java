package com.wing.order.client;

import com.wing.order.entity.ProductInfo;
import com.wing.order.vo.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(value = "product", fallback = ProductClient.ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/products/{productIds}")
    List<ProductInfo> getProductsByIds(@PathVariable("productIds") String[] productIds);

    @PutMapping("/products/decreaseStock")
    void decreaseStock(@RequestBody List<CartDTO> carts);

    @Component
    class ProductClientFallback implements ProductClient {
        @Override
        public List<ProductInfo> getProductsByIds(String[] productIds) {
            return null;
        }

        @Override
        public void decreaseStock(List<CartDTO> carts) {

        }
    }
}
