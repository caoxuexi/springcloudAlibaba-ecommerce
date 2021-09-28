package com.caostudy.ecommerce.service.impl;

/**
 * @author Cao Study
 * @description <h1>OrderServiceImpl</h1>
 * @date 2021/9/28 17:19
 */

import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.account.AddressInfo;
import com.caostudy.ecommerce.account.BalanceInfo;
import com.caostudy.ecommerce.common.TableId;
import com.caostudy.ecommerce.dao.EcommerceOrderDao;
import com.caostudy.ecommerce.entity.EcommerceOrder;
import com.caostudy.ecommerce.feign.AddressClient;
import com.caostudy.ecommerce.feign.NotSecuredBalanceClient;
import com.caostudy.ecommerce.feign.NotSecuredGoodsClient;
import com.caostudy.ecommerce.feign.SecuredGoodsClient;
import com.caostudy.ecommerce.filter.AccessContext;
import com.caostudy.ecommerce.goods.DeductGoodsInventory;
import com.caostudy.ecommerce.goods.SimpleGoodsInfo;
import com.caostudy.ecommerce.order.LogisticsMessage;
import com.caostudy.ecommerce.order.OrderInfo;
import com.caostudy.ecommerce.service.IOrderService;
import com.caostudy.ecommerce.source.LogisticsSource;
import com.caostudy.ecommerce.vo.PageSimpleOrderDetail;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <h1>订单相关服务接口实现</h1>
 * */
@Slf4j
@Service
@EnableBinding(LogisticsSource.class)
public class OrderServiceImpl implements IOrderService {

    /** 表的 dao 接口 */
    private final EcommerceOrderDao orderDao;

    /** Feign 客户端 */
    private final AddressClient addressClient;
    private final SecuredGoodsClient securedGoodsClient;
    private final NotSecuredGoodsClient notSecuredGoodsClient;
    private final NotSecuredBalanceClient notSecuredBalanceClient;

    /** SpringCloud Stream 的发射器 */
    private final LogisticsSource logisticsSource;

    //这里自动注入addressClient和securedGoodsClient会报错，这是因为idea无法识别服务降级类的原因
    //我们可以在Setting-Editor-Inspections设置里面将Autowiring for bean class的报错关掉即可
    public OrderServiceImpl(EcommerceOrderDao orderDao,
                            AddressClient addressClient,
                            SecuredGoodsClient securedGoodsClient,
                            NotSecuredGoodsClient notSecuredGoodsClient,
                            NotSecuredBalanceClient notSecuredBalanceClient,
                            LogisticsSource logisticsSource) {
        this.orderDao = orderDao;
        this.addressClient = addressClient;
        this.securedGoodsClient = securedGoodsClient;
        this.notSecuredGoodsClient = notSecuredGoodsClient;
        this.notSecuredBalanceClient = notSecuredBalanceClient;
        this.logisticsSource = logisticsSource;
    }

    /**
     * <h2>创建订单: 这里会涉及到分布式事务</h2>
     * 创建订单会涉及到多个步骤和校验, 当不满足情况时直接抛出异常;
     * 1. 校验请求对象是否合法
     * 2. 创建订单
     * 3. 扣减商品库存
     * 4. 扣减用户余额
     * 5. 发送订单物流消息 SpringCloud Stream + Kafka
     * */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public TableId createOrder(OrderInfo orderInfo) {

        // 获取地址信息
        AddressInfo addressInfo = addressClient.getAddressInfoByTablesId(
                new TableId(Collections.singletonList(
                        new TableId.Id(orderInfo.getUserAddress())))).getData();

        // 1. 校验请求对象是否合法(商品信息不需要校验, 扣减库存会做校验)
        if (CollectionUtils.isEmpty(addressInfo.getAddressItems())) {
            throw new RuntimeException("user address is not exist: "
                    + orderInfo.getUserAddress());
        }

        // 2. 创建订单
        EcommerceOrder newOrder = orderDao.save(
                new EcommerceOrder(
                        AccessContext.getLoginUserInfo().getId(),
                        orderInfo.getUserAddress(),
                        JSON.toJSONString(orderInfo.getOrderItems())
                )
        );
        log.info("create order success: [{}], [{}]",
                AccessContext.getLoginUserInfo().getId(), newOrder.getId());

        // 3. 扣减商品库存
        if (
                !notSecuredGoodsClient.deductGoodsInventory(
                        orderInfo.getOrderItems()
                                .stream()
                                .map(OrderInfo.OrderItem::toDeductGoodsInventory)
                                .collect(Collectors.toList())
                ).getData()
        ) {
            throw new RuntimeException("deduct goods inventory failure");
        }

        // 4. 使用feign调用接口扣减用户账户余额 这里都使用notSecured无兜底的方法，这样才能抛出Exception进行回滚
        // 4.1 获取商品信息, 计算总价格
        List<SimpleGoodsInfo> goodsInfos = notSecuredGoodsClient.getSimpleGoodsInfoByTableId(
                new TableId(
                        orderInfo.getOrderItems()
                                .stream()
                                .map(o -> new TableId.Id(o.getGoodsId()))
                                .collect(Collectors.toList())
                )
        ).getData();
        Map<Long, SimpleGoodsInfo> goodsId2GoodsInfo = goodsInfos.stream()
                .collect(Collectors.toMap(SimpleGoodsInfo::getId, Function.identity()));
        long balance = 0;
        for (OrderInfo.OrderItem orderItem : orderInfo.getOrderItems()) {
            balance += goodsId2GoodsInfo.get(orderItem.getGoodsId()).getPrice()
                    * orderItem.getCount();
        }
        assert balance > 0;

        // 4.2 填写总价格, 扣减账户余额
        BalanceInfo balanceInfo = notSecuredBalanceClient.deductBalance(
                new BalanceInfo(AccessContext.getLoginUserInfo().getId(), balance)
        ).getData();
        if (null == balanceInfo) {
            throw new RuntimeException("deduct user balance failure");
        }
        log.info("deduct user balance: [{}], [{}]", newOrder.getId(),
                JSON.toJSONString(balanceInfo));

        // 5. 发送订单物流消息 SpringCloud Stream + Kafka
        LogisticsMessage logisticsMessage = new LogisticsMessage(
                AccessContext.getLoginUserInfo().getId(),
                newOrder.getId(),
                orderInfo.getUserAddress(),
                null    // 没有备注信息
        );
        if (!logisticsSource.logisticsOutput().send(
                MessageBuilder.withPayload(JSON.toJSONString(logisticsMessage)).build()
        )) {
            throw new RuntimeException("send logistics message failure");
        }
        log.info("send create order message to kafka with stream: [{}]",
                JSON.toJSONString(logisticsMessage));

        // 返回订单 id
        return new TableId(Collections.singletonList(new TableId.Id(newOrder.getId())));
    }

    @Override
    public PageSimpleOrderDetail getSimpleOrderDetailByPage(int page) {

        if (page <= 0) {
            page = 1;   // 默认是第一页
        }

        // 这里分页的规则是: 1页10条数据, 按照 id 倒序排列
        Pageable pageable = PageRequest.of(page - 1, 10,
                Sort.by("id").descending());
        Page<EcommerceOrder> orderPage = orderDao.findAllByUserId(
                AccessContext.getLoginUserInfo().getId(), pageable
        );
        List<EcommerceOrder> orders = orderPage.getContent();

        // 如果是空, 直接返回空数组
        if (CollectionUtils.isEmpty(orders)) {
            return new PageSimpleOrderDetail(Collections.emptyList(), false);
        }

        // 获取当前订单中所有的 goodsId, 这个 set 不可能为空或者是 null, 否则, 代码一定有 bug
        Set<Long> goodsIdsInOrders = new HashSet<>();
        orders.forEach(o -> {
            List<DeductGoodsInventory> goodsAndCount = JSON.parseArray(
                    o.getOrderDetail(), DeductGoodsInventory.class
            );
            goodsIdsInOrders.addAll(goodsAndCount.stream()
                    .map(DeductGoodsInventory::getGoodsId)
                    .collect(Collectors.toSet()));
        });

        assert CollectionUtils.isNotEmpty(goodsIdsInOrders);

        // 是否还有更多页: 总页数是否大于当前给定的页
        boolean hasMore = orderPage.getTotalPages() > page;

        // 获取商品信息
        List<SimpleGoodsInfo> goodsInfos = securedGoodsClient.getSimpleGoodsInfoByTableId(
                new TableId(goodsIdsInOrders.stream()
                        .map(TableId.Id::new).collect(Collectors.toList()))
        ).getData();

        // 获取地址信息
        AddressInfo addressInfo = addressClient.getAddressInfoByTablesId(
                new TableId(orders.stream()
                        .map(o -> new TableId.Id(o.getAddressId()))
                        .distinct().collect(Collectors.toList()))
        ).getData();

        // 组装订单中的商品, 地址信息 -> 订单信息
        return new PageSimpleOrderDetail(
                assembleSimpleOrderDetail(orders, goodsInfos, addressInfo),
                hasMore
        );
    }

    /**
     * <h2>组装订单详情</h2>
     * */
    private List<PageSimpleOrderDetail.SingleOrderItem> assembleSimpleOrderDetail(
            List<EcommerceOrder> orders, List<SimpleGoodsInfo> goodsInfos,
            AddressInfo addressInfo
    ) {
        // goodsId -> SimpleGoodsInfo   商品id做key值，数据本身作为value[ Function
        Map<Long, SimpleGoodsInfo> id2GoodsInfo = goodsInfos.stream()
                .collect(Collectors.toMap(SimpleGoodsInfo::getId, Function.identity()));
        // addressId -> AddressInfo.AddressItem, 地址id做key值，数据本身作为value[ Function.identity() ]
        Map<Long, AddressInfo.AddressItem> id2AddressItem = addressInfo.getAddressItems()
                .stream().collect(
                        Collectors.toMap(AddressInfo.AddressItem::getId, Function.identity())
                );

        List<PageSimpleOrderDetail.SingleOrderItem> result = new ArrayList<>(orders.size());
        orders.forEach(o -> {

            PageSimpleOrderDetail.SingleOrderItem orderItem =
                    new PageSimpleOrderDetail.SingleOrderItem();
            orderItem.setId(o.getId());
            orderItem.setUserAddress(id2AddressItem.getOrDefault(o.getAddressId(),
                    new AddressInfo.AddressItem(-1L)).toUserAddress());
            orderItem.setGoodsItems(buildOrderGoodsItem(o, id2GoodsInfo));

            result.add(orderItem);
        });

        return result;
    }

    /**
     * <h2>构造订单中的商品信息</h2>
     * */
    private List<PageSimpleOrderDetail.SingleOrderGoodsItem> buildOrderGoodsItem(
            EcommerceOrder order, Map<Long, SimpleGoodsInfo> id2GoodsInfo
    ) {

        List<PageSimpleOrderDetail.SingleOrderGoodsItem> goodsItems = new ArrayList<>();
        List<DeductGoodsInventory> goodsAndCount = JSON.parseArray(
                order.getOrderDetail(), DeductGoodsInventory.class
        );

        goodsAndCount.forEach(gc -> {

            PageSimpleOrderDetail.SingleOrderGoodsItem goodsItem =
                    new PageSimpleOrderDetail.SingleOrderGoodsItem();
            goodsItem.setCount(gc.getCount());
            goodsItem.setSimpleGoodsInfo(id2GoodsInfo.getOrDefault(gc.getGoodsId(),
                    new SimpleGoodsInfo(-1L)));

            goodsItems.add(goodsItem);
        });

        return goodsItems;
    }
}

