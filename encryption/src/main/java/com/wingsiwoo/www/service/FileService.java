package com.wingsiwoo.www.service;

import com.wingsiwoo.www.po.EncryptBo;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
public interface FileService {
    /**
     * 加/解密
     * @param encryptBo encryptBo
     * @return 加/解密后的文件字节数组
     */
    byte[] encrypt(EncryptBo encryptBo);
}
