package com.caostudy.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cao Study
 * @description <h1>UsernameAndPassword 用户名和密码</h1>
 * @date 2021/9/18 13:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameAndPassword {
    //用户名
    public  String username;

    //密码
    public String password;
}
