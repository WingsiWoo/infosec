package com.wingsiwoo.www.service;

/**
 * @author WingsiWoo
 * @date 2021/10/13
 */
public interface EncryptService {
    /**
     * 凯撒加/解密方式
     *
     * @param str    待加密字符串
     * @param offset 偏移量
     * @return 加密后字符串
     */
    String caesar(String str, int offset);

    /**
     * playfair加密方式
     *
     * @param str 待加密字符串
     * @param key 密文
     * @return 加密后字符串
     */
    String playfairEncrypt(String str, String key);

    /**
     * playfair解密方式
     *
     * @param str 待解密字符串
     * @param key 密文
     * @return 解密后字符串
     */
    String playfairDecrypt(String str, String key);

    String hillEncrypt(String str, String key);

    String hillDecrypt(String str, String key);
}
