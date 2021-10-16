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

    @Override
    public String playfairDecrypt(String str, String key) {
        Assert.isTrue(str.matches("[a-zA-Z]+"), "PLAYFAIR解密的字符串必须为全英文字符");
        Assert.isTrue(key.matches("[a-zA-Z]+"), "PLAYFAIR密钥必须为全英文字符");

        // 用于存储字符-下标的映射关系，方便代换
        Map<Character, Coordinate> map = new HashMap<>();
        // 初始化字母矩阵
        char[][] matrix = initMatrix(key, map);

        char[] groupedStr = str.toCharArray();
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
        return checkDecryptedStr(decryptedStr.toString());
    }

    /**
     * 整理解密后的明文字符串，删除填充字符
     *
     * @param decryptedStr 解密后的明文字符串
     * @return 删除填充字符后的明文字符串
     */
    private String checkDecryptedStr(String decryptedStr) {
        StringBuilder builder = new StringBuilder();
        // 解密后的明文字符串长度一定为偶数，因此该循环可以完全覆盖
        for (int i = 0; i < decryptedStr.length(); i += 2) {
            builder.append(decryptedStr.charAt(i));
            // 后者括号内判断式的含义为明文分组时两个相同字符在同一组，在其中间插入一个填充字符的情况
            if (i + 2 < decryptedStr.length() && !(decryptedStr.charAt(i) == decryptedStr.charAt(i + 2) && decryptedStr.charAt(i + 1) == 'w')) {
                builder.append(decryptedStr.charAt(i + 1));
                continue;
            }
            // 该判断式含义为原明文长度为奇数，在最后一个字符后添加一个填充字符成一组
            if (i + 2 == decryptedStr.length()) {
                if (decryptedStr.charAt(i + 1) != 'w') {
                    builder.append(decryptedStr.charAt(i + 1));
                }
            }
        }
        return builder.toString();
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
            map.put(keyChar, new Coordinate(i, j));
            if (j + 1 < 5) {
                j++;
            } else {
                j = 0;
                i++;
            }
        }
        for (; i < 5; i++) {
            for (; ; j++) {
                // 跳过已经出现过的字符
                while (!charSet.add(c)) {
                    c++;
                }
                matrix[i][j] = c;
                map.put(c, new Coordinate(i, j));
                // 把i和j视为同一个字符
                if (c + 1 == 'j') {
                    c += 2;
                }
                // 初始化下一行
                if (j == 4) {
                    j = 0;
                    break;
                }
            }
        }
        return matrix;
    }

    @Override
    public String hillEncrypt(String str, String key) {
        Assert.isTrue(str.matches("[a-zA-Z]+"), "HILL加密的字符串必须为全英文字符");
        Assert.isTrue(key.matches("[a-zA-Z]{4}"), "HILL加密密钥必须四位纯英文字母");

        int[][] keyMatrix = getKeyMatrix(key);
        int[][] strMatrix = getStrMatrix(str);
        int[][] encryptedMatrix = calculateMatrix(keyMatrix, strMatrix);
        return getMatrixStr(encryptedMatrix);
    }

    /**
     * 获取4位英文密钥的对应向量矩阵
     *
     * @param key 密钥
     * @return 对应向量矩阵，0-a/A，1-b/B以此类推
     */
    private int[][] getKeyMatrix(String key) {
        int[][] matrix = new int[2][2];
        int x = 0;
        int y = 0;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            matrix[x][y] = getLetterInt(c);
            y++;
            if (y == 2) {
                y = 0;
                x++;
            }
        }
        Assert.isTrue(matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0] != 0, "矩阵不可逆");
        return matrix;
    }

    @Override
    public String hillDecrypt(String str, String key) {
        Assert.isTrue(str.matches("[a-zA-Z]+"), "HILL解密的字符串必须为全英文字符");
        Assert.isTrue(key.matches("[a-zA-Z]{4}"), "HILL解密密钥必须四位纯英文字母");

        int[][] inverseMatrix = getInverseMatrix(getKeyMatrix(key));

        int[][] strMatrix = getStrMatrix(str);
        int[][] decryptedMatrix = calculateMatrix(inverseMatrix, strMatrix);
        String decryptedStr = getMatrixStr(decryptedMatrix);
        return decryptedStr.charAt(0) == decryptedStr.charAt(decryptedStr.length() - 1) ? decryptedStr.substring(0, decryptedStr.length() - 1) : decryptedStr;
    }

    private int[][] calculateMatrix(int[][] key, int[][] strMatrix) {
        int[][] res = new int[2][strMatrix[0].length];
        // 矩阵运算，密钥矩阵左乘明文矩阵
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < strMatrix[0].length; j++) {
                res[i][j] = Math.floorMod(key[i][0] * strMatrix[0][j] + key[i][1] * strMatrix[1][j], 26);
            }
        }
        return res;
    }

    /**
     * 求逆矩阵
     *
     * @param matrix 矩阵
     * @return 逆矩阵
     */
    private int[][] getInverseMatrix(int[][] matrix) {
        // 二阶矩阵的逆矩阵为伴随矩阵/秩,二阶矩阵的伴随矩阵为主对角线元素互换，副对角线元素取负
        int[][] inverse = new int[2][2];
        // 设从左到右，从上到下依次为abcd，该式表示ad-bc，即该矩阵的秩
        int divisor = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        // 求divisor的逆元
        int multiplier = 1;
        int num = divisor;
        while ((Math.floorMod(num, 26)) != 1) {
            multiplier++;
            num += divisor;
        }

        inverse[0][0] = Math.floorMod(matrix[1][1] * multiplier, 26);
        inverse[0][1] = Math.floorMod(-matrix[0][1] * multiplier, 26);
        inverse[1][0] = Math.floorMod(-matrix[1][0] * multiplier, 26);
        inverse[1][1] = Math.floorMod(matrix[0][0] * multiplier, 26);
        return inverse;
    }

    /**
     * 获取英文字母对应数字，0-a/A，1-b/B以此类推
     *
     * @param c 英文字母
     * @return 对应数字
     */
    private int getLetterInt(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 97;
        } else if (c >= 'A' && c <= 'Z') {
            // 大写英文字母
            return c - 65;
        }
        return -1;
    }

    /**
     * 生成两行n列的向量
     */
    private int[][] getStrMatrix(String str) {
        int[][] matrix = new int[2][(str.length() + 1) / 2];
        int k = 0;
        for (int i = 0; i < (str.length() + 1) / 2 && k < str.length(); i++, k += 2) {
            matrix[0][i] = getLetterInt(str.charAt(k));
            if (k + 1 == str.length()) {
                // 说明字符串长度为单数，在最后补充一个该字符串的第一个字符
                matrix[1][i] = getLetterInt(str.charAt(0));
                break;
            } else {
                matrix[1][i] = getLetterInt(str.charAt(k + 1));
            }
        }
        return matrix;
    }

    /**
     * 获取2行n列向量对应字符串
     *
     * @param matrix 向量矩阵
     * @return 字符串
     */
    private String getMatrixStr(int[][] matrix) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < matrix[0].length; i++) {
            builder.append((char) (matrix[0][i] + 97));
            builder.append((char) (matrix[1][i] + 97));
        }
        return builder.toString();
    }

}
