package com.caostudy.ecommerce.service;
import com.caostudy.ecommerce.account.BalanceInfo;

/**
 * <h2>用于余额相关的服务接口定义</h2>
 * */
public interface IBalanceService {

    /**
     * <h2>获取当前用户余额信息</h2>
     * */
    BalanceInfo getCurrentUserBalanceInfo();

    /**
     * <h2>扣减用户余额</h2>
     * @param balanceInfo 代表想要扣减的余额
     * */
    BalanceInfo deductBalance(BalanceInfo balanceInfo);
}

