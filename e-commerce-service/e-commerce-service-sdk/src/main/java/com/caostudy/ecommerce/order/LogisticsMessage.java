package com.caostudy.ecommerce.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cao Study
 * @description <h1>LogisticsMessage创建订单时发送的物流消息</h1>
 * @date 2021/9/28 17:09
 */
@ApiModel(description = "Stream 物流消息对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsMessage {
    @ApiModelProperty(value = "用户表主键id")
    private Long userId;

    @ApiModelProperty(value = "订单表主键id")
    private Long orderId;

    @ApiModelProperty(value = "用户地址表主键id")
    private Long addressId;

    @ApiModelProperty(value = "备注信息(json存储)")
    private String extraInfo;
}
