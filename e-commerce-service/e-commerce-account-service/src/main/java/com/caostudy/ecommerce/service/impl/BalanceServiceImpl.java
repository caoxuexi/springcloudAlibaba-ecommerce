package com.caostudy.ecommerce.service.impl;

import com.caostudy.ecommerce.account.BalanceInfo;
import com.caostudy.ecommerce.dao.EcommerceBalanceDao;
import com.caostudy.ecommerce.entity.EcommerceBalance;
import com.caostudy.ecommerce.filter.AccessContext;
import com.caostudy.ecommerce.service.IBalanceService;
import com.caostudy.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>用于余额相关服务接口实现</h1>
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceServiceImpl implements IBalanceService {
    @Autowired
    private EcommerceBalanceDao balanceDao;

    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        BalanceInfo balanceInfo = new BalanceInfo(
                loginUserInfo.getId(), 0L
        );

        EcommerceBalance ecommerceBalance =
                balanceDao.findByUserId(loginUserInfo.getId());
        if (null != ecommerceBalance) {
            balanceInfo.setBalance(ecommerceBalance.getBalance());
        } else {
            // 如果还没有用户余额记录, 这里创建出来，余额设定为0即可
            EcommerceBalance newBalance = new EcommerceBalance();
            newBalance.setUserId(loginUserInfo.getId());
            newBalance.setBalance(0L);
            log.info("init user balance record: [{}]",
                    balanceDao.save(newBalance).getId());
        }

        return balanceInfo;
    }

    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        // 扣减用户余额的一个基本原则: 扣减额 <= 当前用户余额
        EcommerceBalance ecommerceBalance =
                balanceDao.findByUserId(loginUserInfo.getId());
        if (null == ecommerceBalance
                || ecommerceBalance.getBalance() - balanceInfo.getBalance() < 0
        ) {
            throw new RuntimeException("user balance is not enough!");
        }

        Long sourceBalance = ecommerceBalance.getBalance();
        ecommerceBalance.setBalance(ecommerceBalance.getBalance() - balanceInfo.getBalance());
        log.info("deduct balance: [{}], [{}], [{}]",
                balanceDao.save(ecommerceBalance).getId(), sourceBalance,
                balanceInfo.getBalance());

        return new BalanceInfo(
                ecommerceBalance.getUserId(),
                ecommerceBalance.getBalance()
        );
    }
}