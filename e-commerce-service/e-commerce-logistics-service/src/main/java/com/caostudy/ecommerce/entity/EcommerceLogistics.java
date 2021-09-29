package com.caostudy.ecommerce.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * <h1>物流表实体类定义</h1>
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_ecommerce_logistics")
public class EcommerceLogistics {

    /** 自增主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /** 用户 id */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** 订单 id */
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    /** 用户地址 id */
    @Column(name = "address_id", nullable = false)
    private Long addressId;

    /** 备注信息(json 存储) */
    @Column(name = "extra_info", nullable = false)
    private String extraInfo;

    /** 创建时间 */
    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /** 更新时间 */
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public EcommerceLogistics(Long userId, Long orderId, Long addressId, String extraInfo) {

        this.userId = userId;
        this.orderId = orderId;
        this.addressId = addressId;
        this.extraInfo = StringUtils.isNotBlank(extraInfo) ? extraInfo : "{}";
    }
}
