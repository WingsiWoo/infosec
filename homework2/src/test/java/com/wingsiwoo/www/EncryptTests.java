package com.wingsiwoo.www;

import com.wingsiwoo.www.service.EncryptService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author WingsiWoo
 * @date 2021/10/14
 */
@SpringBootTest
public class EncryptTests {
    @Resource
    private EncryptService encryptService;

    @Test
    public void caesar() {
        // encrypt
        Assertions.assertEquals("mfqhstvfs", encryptService.caesar("zsdufgisf", 13));
        Assertions.assertEquals("kgwvtwqswgkqrsy", encryptService.caesar("ieutruoqueiopqw", 2));
        // decrypt
        Assertions.assertEquals("zsdufgisf", encryptService.caesar("mfqhstvfs", -13));
        Assertions.assertEquals("ieutruoqueiopqw", encryptService.caesar("kgwvtwqswgkqrsy", -2));
    }

    @Test
    public void playfair() {
        String key = "sgfu";
        Assertions.assertEquals("bhkxiqvpqmcx", encryptService.playfairEncrypt("helloworld", key));
        Assertions.assertEquals("helloworld", encryptService.playfairDecrypt("bhkxiqvpqmcx", key));

        Assertions.assertEquals("gzshcixx", encryptService.playfairEncrypt("aabbkw", key));
        Assertions.assertEquals("aabbkw", encryptService.playfairDecrypt("gzshcixx", key));

        Assertions.assertEquals("xxgpxx", encryptService.playfairEncrypt("wwkw", key));
        Assertions.assertEquals("wwkw", encryptService.playfairDecrypt("xxgpxx", key));
    }

    @Test
    public void hill() {
        int[][] matrix = new int[2][2];
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[1][0] = 0;
        matrix[1][1] = 3;
        // 二阶矩阵的逆矩阵为伴随矩阵/秩,二阶矩阵的伴随矩阵为主对角线元素互换，副对角线元素取负
        int[][] inverse = new int[2][2];
        // 设从左到右，从上到下依次为abcd，该式表示ad-bc，即该矩阵的秩
        int divisor = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        // 对分数取模求得乘数
        int multiplier = 1;
        while (divisor * multiplier % 26 != 1) {
            multiplier++;
        }
        inverse[0][0] = getMod(matrix[1][1] * multiplier);
        inverse[0][1] = getMod(-matrix[0][1] * multiplier);
        inverse[1][0] = getMod(-matrix[1][0] * multiplier);
        inverse[1][1] = getMod(matrix[0][0] * multiplier);
        System.out.println(inverse);
    }

    private int getMod(int num) {
        if (num < 0) {
            return (26 + num) % 26;
        } else {
            return num % 26;
        }
    }
}
