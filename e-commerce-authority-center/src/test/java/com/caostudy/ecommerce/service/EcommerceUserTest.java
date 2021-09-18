package com.caostudy.ecommerce.service;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.dao.EcommerceUserDao;
import com.caostudy.ecommerce.entity.EcommerceUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Cao Study
 * @description EcommerceUserTest
 * @date 2021/9/18 8:44
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EcommerceUserTest {
    @Autowired
    private EcommerceUserDao ecommerceUserDao;

    @Test
    public void createUserRecord(){
        EcommerceUser ecommerceUser=new EcommerceUser();
        ecommerceUser.setUsername("CaoStudy@imooc.com");
        //MD5使用hutools下的方法
        ecommerceUser.setPassword(MD5.create().digestHex("12345678"));
        ecommerceUser.setExtraInfo("{}");

        //剩下的字段交给jpa的审计工具来完成了
        log.info("save user:[{}]", JSON.toJSONString(ecommerceUserDao.save(ecommerceUser)));
    }
}
