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
    public String playfairEncrypt(String str, String key) {
        Assert.isTrue(str.matches("[a-zA-Z]+"), "PLAYFAIR加密的字符串必须为全英文字符");
        Assert.isTrue(key.matches("[a-zA-Z]+"), "PLAYFAIR密钥必须为全英文字符");

        // 用于存储字符-下标的映射关系，方便代换
        Map<Character, Coordinate> map = new HashMap<>();
        // 初始化字母矩阵
        char[][] matrix = initMatrix(key, map);

        // 1.落在矩阵同一行的明文字母对中的字母由其右边的字母来代换，每行中最右边的字母用该行中最左边的字母来代换。
        // 2.落在矩阵同一列的明文字母对中的字母由其下面的字母来代换，每列最下面的字母用该列最上面的字母代换。
        // 3.如果明文字母对中的两个字母既不在同一行，也不在同一列，则由这两个字母确定的矩形的其他两个角的字母代换，（可以事先约定好横向代换或者纵向代换）。
        char[] groupedStr = getGroupedStr(str);
        StringBuilder encryptedStr = new StringBuilder();
        for (int i = 1; i < groupedStr.length; i += 2) {
            Coordinate firstCoordinate = map.get(groupedStr[i - 1]);
            Coordinate secondCoordinate = map.get(groupedStr[i]);
            if (firstCoordinate.getX().equals(secondCoordinate.getX())) {
                // 同一行的情况--用右面的字母替代，如果为最后一列则用第一列替代
                if (firstCoordinate.getY() != 4) {
                    encryptedStr.append(matrix[firstCoordinate.getX()][firstCoordinate.getY() + 1]);
                } else {
                    encryptedStr.append(matrix[firstCoordinate.getX()][0]);
                }
                if (secondCoordinate.getY() != 4) {
                    encryptedStr.append(matrix[secondCoordinate.getX()][secondCoordinate.getY() + 1]);
                } else {
                    encryptedStr.append(matrix[secondCoordinate.getX()][0]);
                }
            } else if (firstCoordinate.getY().equals(secondCoordinate.getY())) {
                // 同一列的情况--用下面的字母替代，如果为最后一行则用第一行替代
                if (firstCoordinate.getX() != 4) {
                    encryptedStr.append(matrix[firstCoordinate.getX() + 1][firstCoordinate.getY()]);
                } else {
                    encryptedStr.append(matrix[0][firstCoordinate.getY()]);
                }
                if (secondCoordinate.getX() != 4) {
                    encryptedStr.append(matrix[secondCoordinate.getX() + 1][secondCoordinate.getY()]);
                } else {
                    encryptedStr.append(matrix[0][secondCoordinate.getY()]);
                }
            } else {
                // 不同行不同列的情况--横向替换
                encryptedStr.append(matrix[firstCoordinate.getX()][secondCoordinate.getY()]);
                encryptedStr.append(matrix[secondCoordinate.getX()][firstCoordinate.getY()]);
            }
        }
        return encryptedStr.toString();
    }

    @Override
    public String playfairDecrypt(String str, String key) {
        Assert.isTrue(str.matches("[a-zA-Z]+"), "PLAYFAIR解密的字符串必须为全英文字符");
        Assert.isTrue(key.matches("[a-zA-Z]+"), "PLAYFAIR密钥必须为全英文字符");

        // 用于存储字符-下标的映射关系，方便代换
        Map<Character, Coordinate> map = new HashMap<>();
        // 初始化字母矩阵
        char[][] matrix = initMatrix(key, map);

        char[] groupedStr = getGroupedStr(str);
        StringBuilder decryptedStr = new StringBuilder();
        for (int i = 1; i < groupedStr.length; i += 2) {
            Coordinate firstCoordinate = map.get(groupedStr[i - 1]);
            Coordinate secondCoordinate = map.get(groupedStr[i]);
            if (firstCoordinate.getX().equals(secondCoordinate.getX())) {
                // 同一行的情况--用左面的字母替代，如果为第一列则用最后一列替代
                if (firstCoordinate.getY() != 0) {
                    decryptedStr.append(matrix[firstCoordinate.getX()][firstCoordinate.getY() - 1]);
                } else {
                    decryptedStr.append(matrix[firstCoordinate.getX()][4]);
                }
                if (secondCoordinate.getY() != 0) {
                    decryptedStr.append(matrix[secondCoordinate.getX()][secondCoordinate.getY() - 1]);
                } else {
                    decryptedStr.append(matrix[secondCoordinate.getX()][4]);
                }
            } else if (firstCoordinate.getY().equals(secondCoordinate.getY())) {
                // 同一列的情况--用上面的字母替代，如果为第一行则用最后一行替代
                if (firstCoordinate.getX() != 0) {
                    decryptedStr.append(matrix[firstCoordinate.getX() - 1][firstCoordinate.getY()]);
                } else {
                    decryptedStr.append(matrix[4][firstCoordinate.getY()]);
                }
                if (secondCoordinate.getX() != 0) {
                    decryptedStr.append(matrix[secondCoordinate.getX() - 1][secondCoordinate.getY()]);
                } else {
                    decryptedStr.append(matrix[4][secondCoordinate.getY()]);
                }
            } else {
                // 不同行不同列的情况--横向替换
                decryptedStr.append(matrix[firstCoordinate.getX()][secondCoordinate.getY()]);
                decryptedStr.append(matrix[secondCoordinate.getX()][firstCoordinate.getY()]);
            }
        }
        return decryptedStr.toString();
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

    /**
     * 整理明文，每两个字符一组，如果一组的字符一样，就在中间插入一个填充字母
     *
     * @param str 明文
     * @return 整理后的明文字符数组
     */
    private char[] getGroupedStr(String str) {
        // 填充字符使用w
        str = str.toLowerCase();
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < str.length(); i += 2) {
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

    /**
     * 初始化字母矩阵
     *
     * @param key 密文
     * @param map 字符-坐标映射表
     * @return 字母矩阵
     */
    private char[][] initMatrix(String key, Map<Character, Coordinate> map) {
        Set<Character> charSet = new HashSet<>();
        char[] keyChars = getDeduplicatedKey(key, charSet);
        // 5*5字母矩阵
        char[][] matrix = new char[5][5];
        char c = 'a';

        // 把密文中未出现过的字母按顺序填入矩阵中
        int i = 0;
        int j = 0;
        for (char keyChar : keyChars) {
            matrix[i][j] = keyChar;
            if (j + 1 < 5) {
                j++;
            } else {
                j = 0;
                i++;
            }
        }
        for ( ; i < 5; i++) {
            for ( ; ; j++) {
                // 跳过已经出现过的字符
                while (!charSet.add(c)) {
                    c++;
                }
                matrix[i][j] = c;
                map.put(c, new Coordinate(i, j));
                // 把i和j视为同一个字符
                if(c + 1 == 'j') {
                    c += 2;
                }
                // 初始化下一行
                if(j == 4) {
                    j = 0;
                    break;
                }
            }
        }
        return matrix;
    }

    @Override
    public String hill(String str) {
        return null;
    }
}
