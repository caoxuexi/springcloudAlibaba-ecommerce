package com.caostudy.ecommerce.stream.cao;

import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.vo.CaoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

/**
 * <h1>使用自定义的通信信道 CaoSource 实现消息的发送</h1>
 * */
@Slf4j
@EnableBinding(CaoSource.class)
public class CaoSendService {

    private final CaoSource caoSource;

    public CaoSendService(CaoSource caoSource) {
        this.caoSource = caoSource;
    }

    /**
     * <h2>使用自定义的输出信道发送消息</h2>
     * */
    public void sendMessage(CaoMessage message) {

        String _message = JSON.toJSONString(message);
        log.info("in CaoSendService send message: [{}]", _message);
        caoSource.caoOutput().send(
                MessageBuilder.withPayload(_message).build()
        );
    }
}