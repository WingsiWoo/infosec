package com.wingsiwoo.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author WingsiWoo
 * @date 2021/9/27
 */
@SpringBootApplication
public class EncryptionApplication {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(EncryptionApplication.class);
        // 解决 java.awt.HeadlessException 异常
        builder.headless(false).web(WebApplicationType.NONE).run(args);

    }
}
