package com.caostudy.ecommerce.feign.hystrix;

import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.common.TableId;
import com.caostudy.ecommerce.feign.SecuredGoodsClient;
import com.caostudy.ecommerce.goods.SimpleGoodsInfo;
import com.caostudy.ecommerce.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * <h1>商品服务熔断降级兜底</h1>
 * */
@Slf4j
@Component
public class GoodsClientHystrix implements SecuredGoodsClient {

    @Override
    public CommonResponse<List<SimpleGoodsInfo>> getSimpleGoodsInfoByTableId(
            TableId tableId) {

        log.error("[goods client feign request error in order service] get simple goods" +
                "error: [{}]", JSON.toJSONString(tableId));
        return new CommonResponse<>(
                -1,
                "[goods client feign request error in order service]",
                Collections.emptyList()
        );
    }
}
