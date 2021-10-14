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

    }
}
