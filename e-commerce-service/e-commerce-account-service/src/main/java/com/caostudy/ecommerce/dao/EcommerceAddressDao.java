package com.caostudy.ecommerce.dao;

import com.caostudy.ecommerce.entity.EcommerceAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <h1>EcommerceAddress Dao 接口定义</h1>
 * */
public interface EcommerceAddressDao extends JpaRepository<EcommerceAddress, Long> {

    /**
     * <h2>根据 用户 id 查询地址信息</h2>
     * */
    List<EcommerceAddress> findAllByUserId(Long userId);
}

