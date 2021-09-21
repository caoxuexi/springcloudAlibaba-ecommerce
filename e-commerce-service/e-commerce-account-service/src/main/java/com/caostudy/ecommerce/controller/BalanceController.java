package com.caostudy.ecommerce.controller;

import com.caostudy.ecommerce.account.BalanceInfo;
import com.caostudy.ecommerce.service.IBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>用户余额服务 Controller</h1>
 * */
@Api(tags = "用户余额服务")
@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final IBalanceService balanceService;

    public BalanceController(IBalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @ApiOperation(value = "当前用户余额", notes = "获取当前用户余额信息", httpMethod = "GET")
    @GetMapping("/current-balance")
    public BalanceInfo getCurrentUserBalanceInfo() {
        return balanceService.getCurrentUserBalanceInfo();
    }

    @ApiOperation(value = "扣减余额", notes = "扣减用于余额", httpMethod = "PUT")
    @PutMapping("/deduct-balance")
    public BalanceInfo deductBalance(@RequestBody BalanceInfo balanceInfo) {
        return balanceService.deductBalance(balanceInfo);
    }
}
