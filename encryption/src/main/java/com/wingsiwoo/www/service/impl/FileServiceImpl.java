package com.wingsiwoo.www.service.impl;

import com.wingsiwoo.www.po.LoginBo;
import com.wingsiwoo.www.service.FileService;
import com.wingsiwoo.www.util.EncryptUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public LoginBo login(LoginBo loginBo) {
        Assert.isTrue(EncryptUtil.checkPassword(loginBo.getUserName(), loginBo.getPassword()), "用户密码错误");
        loginBo.setPassword(null);
        loginBo.setPrivateKey(EncryptUtil.getPrivateKey(loginBo.getUserName()));
        return loginBo;
    }
}
