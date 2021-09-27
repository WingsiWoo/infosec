package com.wingsiwoo.www.service.impl;

import com.wingsiwoo.www.po.EncryptBo;
import com.wingsiwoo.www.service.FileService;
import com.wingsiwoo.www.util.EncryptUtil;
import org.springframework.stereotype.Service;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public byte[] encrypt(final EncryptBo encryptBo) {
        return EncryptUtil.encryptFile(encryptBo.getMultipartFile(), encryptBo.getPrivateKey());
    }
}
