package com.caostudy.ecommerce.entity;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Cao Study
 * @description EcommerceUser 用户表实体类定义
 * @date 2021/9/17 22:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//使用Listener是让jpa对我们的数据修改进行监听，主动帮我们填充创建时间和更新时间
@EntityListeners(AuditingEntityListener.class)
@Table(name="t_ecommerce_user")
public class EcommerceUser implements Serializable {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    //用户名
    @Column(name="username",nullable = false)
    private String username;

    //md5密码
    @Column(name = "password" ,nullable = false)
    private String password;

    //额外的信息，json字符串存储
    @Column(name = "extra_info",nullable = false)
    private String extraInfo;

    //创建时间
    @CreatedDate //需要和@EntityListeners及启动类的@EnableJpaAuditing一起使用
    @Column(name = "create_time",nullable = false)
    private Date createTime;

    //更新时间
    @LastModifiedDate
    @Column(name = "update_time",nullable = false)
    private Date updateTime;

}
