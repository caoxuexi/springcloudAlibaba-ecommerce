package com.caostudy.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Cao Study
 * @description <h1>AccountApplication用户账户微服务启动入口</h1>
 * @date 2021/9/20 21:33
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class,args);
    }
}
