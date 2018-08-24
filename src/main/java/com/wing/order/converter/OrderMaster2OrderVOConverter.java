package com.wing.order.converter;

import com.wing.order.entity.OrderMaster;
import com.wing.order.vo.OrderVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 廖师兄
 * 2017-06-11 22:02
 */
public class OrderMaster2OrderVOConverter {

    public static OrderVO convert(OrderMaster orderMaster) {

        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orderMaster, orderVO);
        return orderVO;
    }

    public static List<OrderVO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
