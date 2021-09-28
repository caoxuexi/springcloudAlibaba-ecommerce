package com.caostudy.ecommerce.controller;

import com.caostudy.ecommerce.common.TableId;
import com.caostudy.ecommerce.order.OrderInfo;
import com.caostudy.ecommerce.service.IOrderService;
import com.caostudy.ecommerce.vo.PageSimpleOrderDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>订单服务对外 HTTP 接口</h1>
 * */
@Api(tags = "订单服务")
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(
            value = "创建",
            notes = "购买(分布式事务): 创建订单 -> 扣减库存 -> 扣减余额 -> 发送物流消息",
            httpMethod = "POST"
    )
    @PostMapping("/create-order")
    public TableId createOrder(@RequestBody OrderInfo orderInfo) {
        return orderService.createOrder(orderInfo);
    }

    @ApiOperation(
            value = "订单信息",
            notes = "获取当前用户的订单信息: 带有分页",
            httpMethod = "GET"
    )
    @GetMapping("/order-detail")
    public PageSimpleOrderDetail getSimpleOrderDetailByPage(
            @RequestParam(required = false, defaultValue = "1") int page) {
        return orderService.getSimpleOrderDetailByPage(page);
    }
}
