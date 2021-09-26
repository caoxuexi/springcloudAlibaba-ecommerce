package com.caostudy.ecommerce.stream;

import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.vo.CaoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * <h1>使用默认的通信信道实现消息的发送</h1>
 * */
@Slf4j
@EnableBinding(Source.class)
public class DefaultSendService {

    private final Source source;

    public DefaultSendService(Source source) {
        this.source = source;
    }

    /**
     * <h2>使用默认的输出信道发送消息</h2>
     * */
    public void sendMessage(CaoMessage message) {

        String _message = JSON.toJSONString(message);
        log.info("in DefaultSendService send message: [{}]", _message);

        // Spring Messaging, 统一消息的编程模型, 是 Stream 组件的重要组成部分之一
        source.output().send(MessageBuilder.withPayload(_message).build());
    }
}
