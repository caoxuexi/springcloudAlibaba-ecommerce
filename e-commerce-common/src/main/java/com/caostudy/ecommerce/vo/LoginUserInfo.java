package com.caostudy.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cao Study
 * @description <h1>LoginUserInfo 登录用户信息</h1>
 * @date 2021/9/18 13:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfo {
    //用户id
    private Long id;

    //用户名
    private String username;
}
