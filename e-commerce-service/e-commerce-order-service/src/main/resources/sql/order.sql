-- 创建 t_ecommerce_order 数据表
CREATE TABLE IF NOT EXISTS `imooc_e_commerce`.`t_ecommerce_order`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `user_id`      bigint(20) NOT NULL DEFAULT 0 COMMENT '用户 id',
    `address_id`   bigint(20) NOT NULL DEFAULT 0 COMMENT '用户地址记录 id',
    `order_detail` text       NOT NULL COMMENT '订单详情(json 存储, goodsId, count)',
    `create_time`  datetime   NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '创建时间',
    `update_time`  datetime   NOT NULL DEFAULT '0000-01-01 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8 COMMENT ='用户订单表';
