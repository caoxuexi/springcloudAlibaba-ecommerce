package com.caostudy.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.account.BalanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <h1>用于余额相关服务测试</h1>
 * */
@Slf4j
public class BalanceServiceTest extends BaseTest {

    @Autowired
    private IBalanceService balanceService;

    /**
     * <h2>测试获取当前用户的余额信息</h2>
     * */
    @Test
    public void testGetCurrentUserBalanceInfo() {

        log.info("test get current user balance info: [{}]", JSON.toJSONString(
                balanceService.getCurrentUserBalanceInfo()
        ));
    }

    /**
     * <h2>测试扣减用于余额</h2>
     * */
    @Test
    public void testDeductBalance() {

        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo.setUserId(loginUserInfo.getId());
        balanceInfo.setBalance(1000L);

        log.info("test deduct balance: [{}]", JSON.toJSONString(
                balanceService.deductBalance(balanceInfo)
        ));
    }
}