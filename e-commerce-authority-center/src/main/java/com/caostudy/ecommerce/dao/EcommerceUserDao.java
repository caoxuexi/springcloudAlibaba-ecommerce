package com.caostudy.ecommerce.dao;

import com.caostudy.ecommerce.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Cao Study
 * @description  <h1>EcommerceUserDao 接口定义 </h1>
 * @date 2021/9/17 22:57
 */
public interface EcommerceUserDao extends JpaRepository<EcommerceUser,Long> {
    /**
     * <h2>根据用户名查询 EcommerceUser 对象</h2>
     * select * from t_ecommerce_user where username = ?
     * */
    EcommerceUser findByUsername(String username);

    /**
     * <h2>根据用户名和密码查询实体对象</h2>
     * select * from t_ecommerce_user where username = ? and password = ?
     * */
    EcommerceUser findByUsernameAndPassword(String username, String password);

}
