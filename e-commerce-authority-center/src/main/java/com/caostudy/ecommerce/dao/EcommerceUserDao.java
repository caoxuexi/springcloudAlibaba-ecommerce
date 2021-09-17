package com.caostudy.ecommerce.dao;

import com.caostudy.ecommerce.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Cao Study
 * @description EcommerceUserDao
 * @date 2021/9/17 22:57
 */
public interface EcommerceUserDao extends JpaRepository<EcommerceUser,Long> {


}
