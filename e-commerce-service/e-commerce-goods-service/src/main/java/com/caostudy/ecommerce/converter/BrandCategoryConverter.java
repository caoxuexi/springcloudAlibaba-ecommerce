package com.caostudy.ecommerce.converter;

import com.caostudy.ecommerce.constant.BrandCategory;

import javax.persistence.AttributeConverter;

/**
 * <h1>品牌分类枚举属性转换器</h1>
 * */
public class BrandCategoryConverter implements AttributeConverter<BrandCategory, String> {

    @Override
    public String convertToDatabaseColumn(BrandCategory brandCategory) {
        return brandCategory.getCode();
    }

    @Override
    public BrandCategory convertToEntityAttribute(String code) {
        return BrandCategory.of(code);
    }
}

