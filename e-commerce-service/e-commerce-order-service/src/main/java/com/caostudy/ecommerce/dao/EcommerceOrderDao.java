package com.caostudy.ecommerce.dao;

import com.caostudy.ecommerce.entity.EcommerceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * <h1>EcommerceOrder Dao 接口定义</h1>
 * */
public interface EcommerceOrderDao extends PagingAndSortingRepository<EcommerceOrder, Long> {

    /**
     * <h2>根据 userId 查询分页订单</h2>
     * select * from t_ecommerce_order where user_id = ?
     *  order by ... desc/asc limit x offset y
     * */
    Page<EcommerceOrder> findAllByUserId(Long userId, Pageable pageable);
}
