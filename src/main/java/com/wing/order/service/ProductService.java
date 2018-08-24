package com.wing.order.service;

import com.wing.order.entity.ProductInfo;
import com.wing.order.enums.ResultEnum;
import com.wing.order.exception.SellException;
import com.wing.order.repository.ProductInfoRepository;
import com.wing.order.vo.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductInfoRepository repository;

    public Optional<ProductInfo> findOne(String productId) {
        return repository.findById(productId);
    }

    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElseThrow(() -> new SellException(ResultEnum.PRODUCT_NOT_EXIST));
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }

    }

    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElseThrow(() -> new SellException(ResultEnum.PRODUCT_NOT_EXIST));
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            repository.save(productInfo);
        }
    }


}
