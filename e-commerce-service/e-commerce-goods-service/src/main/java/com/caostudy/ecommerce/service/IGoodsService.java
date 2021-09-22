package com.caostudy.ecommerce.service;

import com.caostudy.ecommerce.common.TableId;
import com.caostudy.ecommerce.goods.DeductGoodsInventory;
import com.caostudy.ecommerce.goods.GoodsInfo;
import com.caostudy.ecommerce.goods.SimpleGoodsInfo;
import com.caostudy.ecommerce.vo.PageSimpleGoodsInfo;

import java.util.List;

/**
 * @author Cao Study
 * @description <h1>IGoodsService 商品微服务相关服务接口定义</h1>
 * @date 2021/9/22 11:08
 */
public interface IGoodsService {
    /**
     * <h2>根据 TableId 查询商品详细信息</h2>
     * */
    List<GoodsInfo> getGoodsInfoByTableId(TableId tableId);

    /**
     * <h2>获取分页的商品信息</h2>
     * */
    PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page);

    /**
     * <h2>根据 TableId 查询简单商品信息</h2>
     * */
    List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId);

    /**
     * <h2>扣减商品库存</h2>
     * */
    Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories);
}
