package com.caostudy.ecommerce;

/**
 * @author Cao Study
 * @description <h1>LogisticsApplication</h1>
 * @date 2021/9/28 22:57
 */

import com.caostudy.ecommerce.conf.DataSourceProxyAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * <h1>物流微服务启动入口</h1>
 * */
@Import(DataSourceProxyAutoConfiguration.class)
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class LogisticsApplication {

    public static void main(String[] args) {

        SpringApplication.run(LogisticsApplication.class, args);
    }
}

