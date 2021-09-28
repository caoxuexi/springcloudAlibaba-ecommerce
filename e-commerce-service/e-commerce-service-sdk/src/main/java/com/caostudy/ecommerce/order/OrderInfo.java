package com.caostudy.ecommerce.order;

import com.caostudy.ecommerce.goods.DeductGoodsInventory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Cao Study
 * @description <h1>OrderInfo</h1>
 * @date 2021/9/26 22:43
 */
@ApiModel(description = "用户发起购买订单")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {
    @ApiModelProperty(value = "用户地址表主键id")
    private Long userAddress;

    @ApiModelProperty(value = "订单中的商品信息")
    private List<OrderItem> orderItems;

    /**
     * <h2>订单中的商品信息</h2>
     */
    @ApiModel(description = "订单中的😰商品信息")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        @ApiModelProperty(value="商品表主键id")
        private Long goodsId;
        @ApiModelProperty(value = "购买商品个数")
        private Integer count;
        public DeductGoodsInventory toDeductGoodsInventory(){
            return new DeductGoodsInventory(this.goodsId,this.count);
        }
    }
}
