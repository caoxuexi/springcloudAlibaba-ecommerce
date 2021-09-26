package com.caostudy.ecommerce.stream.cao;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * <h1>自定义输入信道</h1>
 * */
public interface CaoSink {

    String INPUT = "caoInput";

    /** 输入信道的名称是 caoInput, 需要使用 Stream 绑定器在 yml 文件中配置*/
    @Input(CaoSink.INPUT)
    SubscribableChannel caoInput();
}