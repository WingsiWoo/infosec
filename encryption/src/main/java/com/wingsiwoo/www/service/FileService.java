package com.wingsiwoo.www.service;

import com.wingsiwoo.www.po.LoginBo;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
public interface FileService {
    /**
     * 用户登录
     *
     * @param loginBo 登录的账号密码
     * @return 密码清空，填充密钥
     */
    LoginBo login(LoginBo loginBo);
}
