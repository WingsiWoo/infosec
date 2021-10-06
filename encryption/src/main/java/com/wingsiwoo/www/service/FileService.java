package com.wingsiwoo.www.service;

import java.io.File;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
public interface FileService {
    /**
     * 加/解密接口
     *
     * @param file       待处理文件
     * @param privateKey 密钥
     * @return 处理后的文件路径
     */
    String encrypt(File file, String privateKey);
}
