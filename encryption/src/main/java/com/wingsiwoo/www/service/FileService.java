package com.wingsiwoo.www.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
public interface FileService {
    /**
     * 加/解密
     *
     * @param multipartFile 待加/解密文件
     * @param privateKey    密钥
     * @param response      response
     */
    void encrypt(MultipartFile multipartFile, String privateKey, HttpServletResponse response);
}
