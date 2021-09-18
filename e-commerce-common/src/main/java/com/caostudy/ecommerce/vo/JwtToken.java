package com.caostudy.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cao Study
 * @description <h1>JwtToken 授权中心鉴权之后给客户端的Token</h1>
 * @date 2021/9/18 13:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtToken {
    /** JWT */
    private String token;
}
