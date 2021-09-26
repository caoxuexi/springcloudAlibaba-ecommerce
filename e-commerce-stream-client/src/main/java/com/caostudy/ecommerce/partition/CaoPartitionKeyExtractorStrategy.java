package com.caostudy.ecommerce.partition;

import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.vo.CaoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * <h1>自定义从 Message 中提取 partition key 的策略</h1>
 * */
@Slf4j
@Component
public class CaoPartitionKeyExtractorStrategy implements PartitionKeyExtractorStrategy {

    @Override
    public Object extractKey(Message<?> message) {

        CaoMessage caoMessage = JSON.parseObject(
                message.getPayload().toString(), CaoMessage.class
        );
        // 自定义提取 key
        String key = caoMessage.getProjectName();
        log.info("SpringCloud Stream Cao Partition Key: [{}]", key);
        return key;
    }
}
