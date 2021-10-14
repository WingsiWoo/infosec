package com.wingsiwoo.www.service;

/**
 * @author WingsiWoo
 * @date 2021/10/13
 */
public interface EncryptService {
    /**
     * 凯撒加/解密方式
     * @param str 待加密字符串
     * @param offset 偏移量
     * @return 加密后字符串
     */
    String caesar(String str, int offset);

    String playfairEncrypt(String str, String key);

    String playfairDecrypt(String str, String key);

    String hill(String str);
}
