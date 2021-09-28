package com.caostudy.ecommerce.conf;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author Cao Study
 * @description <h1>DataSourceProxyAutoConfiguration</h1>
 * @date 2021/9/28 16:30
 */
public class DataSourceProxyAutoConfiguration {
    //这个DataSourceProperties 是自动装配了我们配置文件中定义的spring.datasource
    @Autowired
    private DataSourceProperties dataSourceProperties;

    /**
     * <h2>配置数据源代理，用于Seata全局事务回滚</h2>
     * before image+after image->undo_log
     */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(){
        HikariDataSource dataSource =new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        return new DataSourceProxy(dataSource);
    }
}
