package com.caostudy.ecommerce.conf;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <h1>开启服务间的调用保护, 需要给 RestTemplate 做一些包装</h1>
 * */
@Slf4j
@Configuration
public class SentinelConfig {

    /**
     * <h2>包装 RestTemplate</h2>
     * */
    @Bean
//    @SentinelRestTemplate(
//            //fallback很类似hystrix熔断时候的兜底策略
//            fallback = "handleFallback", fallbackClass = RestTemplateExceptionUtil.class,
//            //block配置的是被限流时的处理
//            blockHandler = "handleBlock", blockHandlerClass = RestTemplateExceptionUtil.class
//    )
    public RestTemplate restTemplate() {
        return new RestTemplate();  // 可以对其做一些业务相关的配置
    }
}