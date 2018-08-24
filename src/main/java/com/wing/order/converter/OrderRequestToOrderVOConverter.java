package com.wing.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wing.order.entity.OrderDetail;
import com.wing.order.enums.ResultEnum;
import com.wing.order.exception.SellException;
import com.wing.order.vo.OrderRequestVO;
import com.wing.order.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class OrderRequestToOrderVOConverter {

    public static OrderVO convert(OrderRequestVO orderRequestVO) {
        Gson gson = new Gson();
        OrderVO orderVO= new OrderVO();

        orderVO.setBuyerName(orderRequestVO.getName());
        orderVO.setBuyerPhone(orderRequestVO.getPhone());
        orderVO.setBuyerAddress(orderRequestVO.getAddress());
        orderVO.setBuyerOpenid(orderRequestVO.getOpenid());

        try {
            List<OrderDetail> orderDetails = gson.fromJson(orderRequestVO.getItems(),
                    new TypeToken<List<OrderDetail>>() {}.getType());
            orderVO.setOrderDetails(orderDetails);
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderRequestVO.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        return orderVO;
    }

}
