package com.caostudy.ecommerce.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.caostudy.ecommerce.block_handler.CaoBlockHandler;
import com.caostudy.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>基于 Sentinel 控制台配置流控规则</h1>
 * Sentinel 是懒加载的, 先去访问一下, 就可以在 Sentinel Dashboard 看到了
 * */
@Slf4j
@RestController
@RequestMapping("/dashboard")
public class RateLimitController {

    /**
     * <h2>在 dashboard 中 "流控规则" 中按照资源名称新增流控规则</h2>
     * */
    @GetMapping("/by-resource")
    @SentinelResource(
            value = "byResource",
            blockHandler = "caoHandleBlockException",
            blockHandlerClass = CaoBlockHandler.class
    )
    public CommonResponse<String> byResource() {
        log.info("coming in rate limit controller by resource");
        return new CommonResponse<>(0, "", "byResource");
    }

    /**
     * <h2>在 "簇点链路" 中给 url 添加流控规则</h2>
     * */
    @GetMapping("/by-url")
    @SentinelResource(value = "byUrl")
    public CommonResponse<String> byUrl() {
        log.info("coming in rate limit controller by url");
        return new CommonResponse<>(0, "", "byUrl");
    }
}
