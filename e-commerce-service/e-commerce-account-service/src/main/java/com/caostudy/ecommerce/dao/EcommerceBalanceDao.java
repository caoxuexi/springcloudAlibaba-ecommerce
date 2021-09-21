package com.caostudy.ecommerce.dao;

import com.caostudy.ecommerce.entity.EcommerceBalance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>EcommerceBalance Dao 接口定义</h1>
 * */
public interface EcommerceBalanceDao extends JpaRepository<EcommerceBalance, Long> {

    /** 根据 userId 查询 EcommerceBalance 对象 */
    EcommerceBalance findByUserId(Long userId);
}
