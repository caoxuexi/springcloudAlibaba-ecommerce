package com.caostudy.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cao Study
 * @description <h1>CaoMessage</h1>
 * @date 2021/9/25 18:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaoMessage {

    private Integer id;
    private String projectName;
    private String org;
    private String author;
    private String version;

    /**
     * <h2>返回一个默认的消息, 方便使用</h2>
     * */
    public static CaoMessage defaultMessage() {
        return new CaoMessage(
                1,
                "e-commerce-stream-client",
                "imooc.com",
                "CaoStudy",
                "1.0"
        );
    }
}
