package com.caostudy.ecommerce.fallback_handler;

import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.vo.JwtToken;
import com.caostudy.ecommerce.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;

/**
 * <h1>Sentinel 回退降级的兜底策略</h1>
 * 都需要是静态方法
 * */
@Slf4j
public class CaoFallbackHandler {

    /**
     * <h2>getTokenFromAuthorityService 方法的 fallback</h2>
     * */
    public static JwtToken getTokenFromAuthorityServiceFallback(
            UsernameAndPassword usernameAndPassword
    ) {
        log.error("get token from authority service fallback: [{}]",
                JSON.toJSONString(usernameAndPassword));
        return new JwtToken("imooc-qinyi-fallback");
    }

    /**
     * <h2>ignoreException 方法的 fallback</h2>
     * */
    public static JwtToken ignoreExceptionFallback(Integer code) {
        log.error("ignore exception input code: [{}] has trigger exception", code);
        return new JwtToken("imooc-qinyi-fallback");
    }
}
