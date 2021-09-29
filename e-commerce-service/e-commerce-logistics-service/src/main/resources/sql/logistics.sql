-- 创建 t_ecommerce_logistics 数据表
CREATE TABLE IF NOT EXISTS `imooc_e_commerce`.`t_ecommerce_logistics`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `user_id`     bigint(20)   NOT NULL DEFAULT 0 COMMENT '用户 id',
    `order_id`    bigint(20)   NOT NULL DEFAULT 0 COMMENT '订单 id',
    `address_id`  bigint(20)   NOT NULL DEFAULT 0 COMMENT '用户地址记录 id',
    `extra_info`  varchar(512) NOT NULL COMMENT '备注信息(json 存储)',
    `create_time` datetime     NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '创建时间',
    `update_time` datetime     NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8 COMMENT ='物流表';
