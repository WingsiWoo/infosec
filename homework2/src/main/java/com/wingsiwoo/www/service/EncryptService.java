package com.wingsiwoo.www.service;

/**
 * @author WingsiWoo
 * @date 2021/10/13
 */
public interface EncryptService {
    String caesar(String str, int offset);

    String playfair(String str);

    String hill(String str);
}
