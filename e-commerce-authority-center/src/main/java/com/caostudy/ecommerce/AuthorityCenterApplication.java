package com.caostudy.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Cao Study
 * @description AuthorityCenterApplication 授权中心启动入口
 * @date 2021/9/17 20:37
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing  //允许jpa的自动审计
public class AuthorityCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorityCenterApplication.class, args);
    }
}
