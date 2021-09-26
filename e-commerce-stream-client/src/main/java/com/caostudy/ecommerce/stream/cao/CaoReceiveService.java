package com.caostudy.ecommerce.stream.cao;

import com.alibaba.fastjson.JSON;
import com.caostudy.ecommerce.vo.CaoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * <h1>使用自定义的输入信道实现消息的接收</h1>
 * */
@Slf4j
@EnableBinding(CaoSink.class)
public class CaoReceiveService {

    /** 使用自定义的输入信道接收消息 */
    @StreamListener(CaoSink.INPUT)
    public void receiveMessage(@Payload Object payload) {

        log.info("in CaoReceiveService consume message start");
        CaoMessage caoMessage = JSON.parseObject(payload.toString(), CaoMessage.class);
        log.info("in CaoReceiveService consume message success: [{}]",
                JSON.toJSONString(caoMessage));
    }
}
