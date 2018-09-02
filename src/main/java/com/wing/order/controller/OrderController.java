package com.wing.order.controller;

import com.wing.order.converter.OrderRequestToOrderVOConverter;
import com.wing.order.enums.ResultEnum;
import com.wing.order.exception.SellException;
import com.wing.order.service.OrderService;
import com.wing.order.util.ResultVOUtil;
import com.wing.order.vo.OrderRequestVO;
import com.wing.order.vo.OrderVO;
import com.wing.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;
//
//    @Autowired
//    private BuyerService buyerService;

    //创建订单
    @PostMapping
    public ResultVO<String> create(@RequestBody @Valid OrderRequestVO orderRequest) {
        OrderVO orderVO = OrderRequestToOrderVOConverter.convert(orderRequest);
        if (CollectionUtils.isEmpty(orderVO.getOrderDetails())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        return ResultVOUtil.success(orderService.create(orderVO));
    }

    //订单列表
    @GetMapping
    public ResultVO<List<OrderVO>> list(@RequestParam("openid") String openid,
                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = PageRequest.of(page, size);
        Page<OrderVO> orderDTOPage = orderService.getOrderDetailByOpenid(openid, request);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单列表
    @PostMapping("/finish")
    public ResultVO<OrderVO> finish(OrderVO order) {
        OrderVO orderVO = orderService.finish(order);
        return ResultVOUtil.success(orderVO);
    }



}
