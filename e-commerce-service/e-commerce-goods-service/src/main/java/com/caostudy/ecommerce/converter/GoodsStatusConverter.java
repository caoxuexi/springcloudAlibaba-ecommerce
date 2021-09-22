package com.caostudy.ecommerce.converter;

import com.caostudy.ecommerce.constant.GoodsStatus;

import javax.persistence.AttributeConverter;

/**
 * <h1>商品状态枚举属性转换器</h1>
 * */
public class GoodsStatusConverter implements AttributeConverter<GoodsStatus, Integer> {

    /**
     * <h2>转换成可以存入数据表的基本类型</h2>
     * */
    @Override
    public Integer convertToDatabaseColumn(GoodsStatus goodsStatus) {
        return goodsStatus.getStatus();
    }

    /**
     * <h2>还原数据表中的字段值到 Java 数据类型</h2>
     * */
    @Override
    public GoodsStatus convertToEntityAttribute(Integer status) {
        return GoodsStatus.of(status);
    }
}