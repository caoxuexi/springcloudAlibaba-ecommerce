package com.caostudy.ecommerce.controller;

import com.caostudy.ecommerce.stream.DefaultSendService;
import com.caostudy.ecommerce.stream.cao.CaoSendService;
import com.caostudy.ecommerce.vo.CaoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>构建消息驱动</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private final DefaultSendService defaultSendService;
    private final CaoSendService caoSendService;

    public MessageController(DefaultSendService defaultSendService,
                             CaoSendService caoSendService) {
        this.defaultSendService = defaultSendService;
        this.caoSendService = caoSendService;
    }

    /**
     * <h2>默认信道</h2>
     * */
    @GetMapping("/default")
    public void defaultSend() {
        defaultSendService.sendMessage(CaoMessage.defaultMessage());
    }

    /**
     * <h2>自定义信道</h2>
     * */
    @GetMapping("/cao")
    public void caoSend() {
        caoSendService.sendMessage(CaoMessage.defaultMessage());
    }
}

