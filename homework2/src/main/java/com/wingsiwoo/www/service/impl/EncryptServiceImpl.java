package com.wingsiwoo.www.service.impl;

import com.wingsiwoo.www.service.EncryptService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author WingsiWoo
 * @date 2021/10/13
 */
@Service
public class EncryptServiceImpl implements EncryptService {

    @Override
    public String caesar(String str, int offset) {
        Assert.isTrue(str.matches("[a-zA-Z]+"), "Caesar加密的字符串必须为全英文字符");
        StringBuilder builder = new StringBuilder();
        // 26个英文字母，对其取模
        offset %= 26;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 当前字符为小写字母
            if(c >= 'a' && c <= 'z') {
                c += offset;
                // 偏移后向左越界
                if(c < 'a') {
                    c += 26;
                } else if(c > 'z') {
                    // 偏移后向右越界
                    c -= 26;
                }
            } else if(c >= 'A' && c <= 'Z') {
                c += offset;
                if(c < 'A') {
                    c += 26;
                } else if(c > 'Z') {
                    c -= 26;
                }
            }
            builder.append(c);
        }
        return builder.toString();
    }

    @Override
    public String playfair(String str) {
        return null;
    }

    @Override
    public String hill(String str) {
        return null;
    }
}
