package com.caostudy.ecommerce.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>配置登录请求转发规则</h1>
 * */
@Configuration
public class RouteLocatorConfig {

    /**
     * <h2>使用代码定义路由规则（复杂配置建议还是使用配置文件）
     * 在网关层面拦截下登录和注册接口</h2>
     * */
    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder) {
        // 手动定义 Gateway 路由规则需要指定 id(无特殊规则，不重复即可)、path(匹配路径)
        // 和 uri(path中匹配到后的转发路径,我们这里转发到自身，是因为已经用nacos上的配置文件配置了)
        return builder.routes()
                .route(
                        "e_commerce_authority",
                        r -> r.path(
                                "/caostudy/e-commerce/login",
                                "/caostudy/e-commerce/register"
                                ).uri("http://localhost:9001/")
                ).build();
    }
}