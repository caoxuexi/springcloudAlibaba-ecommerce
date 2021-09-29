package com.caostudy.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.dao.EcommerceLogisticsDao;
import com.caostudy.ecommerce.entity.EcommerceLogistics;
import com.caostudy.ecommerce.order.LogisticsMessage;
import com.caostudy.ecommerce.sink.LogisticsSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * <h1>物流服务实现</h1>
 * */
@Slf4j
@EnableBinding(LogisticsSink.class)
public class LogisticsServiceImpl {

    private final EcommerceLogisticsDao logisticsDao;

    public LogisticsServiceImpl(EcommerceLogisticsDao logisticsDao) {
        this.logisticsDao = logisticsDao;
    }

    /**
     * <h2>订阅监听订单微服务发送的物流消息</h2>
     * */
    @StreamListener("logisticsInput")
    public void consumeLogisticsMessage(@Payload Object payload) {
        log.info("receive and consume logistics message: [{}]", payload.toString());
        LogisticsMessage logisticsMessage = JSON.parseObject(
                payload.toString(), LogisticsMessage.class
        );
        EcommerceLogistics ecommerceLogistics = logisticsDao.save(
                new EcommerceLogistics(
                        logisticsMessage.getUserId(),
                        logisticsMessage.getOrderId(),
                        logisticsMessage.getAddressId(),
                        logisticsMessage.getExtraInfo()
                )
        );
        log.info("consume logistics message success: [{}]", ecommerceLogistics.getId());
    }
}
