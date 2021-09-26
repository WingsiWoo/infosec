package com.wingsiwoo.www.po;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@Data
public class LoginBo {
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String password;

    private String privateKey;
}
