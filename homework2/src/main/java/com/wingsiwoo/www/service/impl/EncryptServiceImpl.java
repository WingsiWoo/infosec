package com.wingsiwoo.www.service.impl;

import com.wingsiwoo.www.entity.Coordinate;
import com.wingsiwoo.www.service.EncryptService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
            if (c >= 'a' && c <= 'z') {
                c += offset;
                // 偏移后向左越界
                if (c < 'a') {
                    c += 26;
                } else if (c > 'z') {
                    // 偏移后向右越界
                    c -= 26;
                }
            } else if (c >= 'A' && c <= 'Z') {
                c += offset;
                if (c < 'A') {
                    c += 26;
                } else if (c > 'Z') {
                    c -= 26;
                }
            }
            builder.append(c);
        }
        return builder.toString();
    }

    @Override
    public String playfair(String str, String key) {
        Assert.isTrue(str.matches("[a-zA-Z]+"), "PLAYFAIR加密的字符串必须为全英文字符");
        Assert.isTrue(key.matches("[a-zA-Z]+"), "PLAYFAIR密钥必须为全英文字符");

        // 5*5字母矩阵
        char[][] matrix = new char[5][5];
        Set<Character> charSet = new HashSet<>();
        char[] keyChars = getDeduplicatedKey(key, charSet);
        // 用于存储字符-下标的映射关系，方便代换
        Map<Character, Coordinate> map = new HashMap<>();

        // 填充字符为w,横向替换

        // 初始化字母矩阵
        char k = 'a';
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i + j < keyChars.length) {
                    // 把去重后的密文字符数组按顺序填入矩阵中
                    matrix[i][j] = keyChars[i + j];
                    map.put(keyChars[i + j], new Coordinate(i, j));
                } else {
                    // 把密文中未出现过的字母按顺序填入矩阵中
                    if (charSet.add(k)) {
                        matrix[i][j] = k;
                        map.put(k, new Coordinate(i, j));
                    }
                    k++;
                }
            }
        }


        // 明文对换
        // 整理明文，每两个字符一组，如果一组的字符一样，就在中间插入一个填充字母
        // 1.落在矩阵同一行的明文字母对中的字母由其右边的字母来代换，每行中最右边的字母用该行中最左边的字母来代换。
        // 2.落在矩阵同一列的明文字母对中的字母由其下面的字母来代换，每列最下面的字母用该列最上面的字母代换。
        // 3.如果明文字母对中的两个字母既不在同一行，也不在同一列，则由这两个字母确定的矩形的其他两个角的字母代换，（可以事先约定好横向代换或者纵向代换）。
        char[] groupedStr = getGroupedStr(str);


        return null;
    }

    /**
     * 获取去重后的密文字符数组
     *
     * @param key 密文字符串
     * @return 字符数组（字符按照原顺序）
     */
    private char[] getDeduplicatedKey(String key, Set<Character> charSet) {
        key = key.toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (charSet.add(c)) {
                builder.append(c);
            }
        }
        return builder.toString().toCharArray();
    }

    private char[] getGroupedStr(String str) {
        // 填充字符使用w
        str = str.toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (int i = 1; ; i += 2) {
            // 位于下标i-1和i的字符为一组
            if (str.charAt(i - 1) == str.charAt(i)) {
                // 如果一组的两个字符相同，则在中间插入填充字符w
                builder.append(str.charAt(i - 1));
                builder.append('w');
                // 使得下一次i指向重复字符后者的下一个字符
                i--;
            } else {
                builder.append(str.charAt(i - 1));
                builder.append(str.charAt(i));
            }
            // 处理本次分组后只剩下一个字符的情况
            if (i + 1 == str.length() - 1) {
                builder.append(str.charAt(i + 1));
                builder.append('w');
                break;
            }
        }
        return builder.toString().toCharArray();
    }

    @Override
    public String hill(String str) {
        return null;
    }
}
