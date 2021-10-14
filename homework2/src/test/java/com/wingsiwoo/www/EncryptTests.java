package com.wingsiwoo.www;

import com.wingsiwoo.www.service.EncryptService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
        Assertions.assertEquals("bhkxiqvpqmcx", encryptService.playfairEncrypt("helloworld", "sgfu"));
        Assertions.assertEquals("helloworld", encryptService.playfairDecrypt("bhkxiqvpqmcx", "sgfu"));
    }

    @Test
    public void hill() {

    }
}
