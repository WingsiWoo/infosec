package com.wingsiwoo.www.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
public class EncryptUtil {
    public static byte[] encryptFile(MultipartFile multipartFile, String privateKey) {
        try (InputStream inputStream = multipartFile.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] keyBytes = privateKey.getBytes(StandardCharsets.UTF_8);
            byte[] buffer = new byte[1024];
            while (inputStream.available() > 0) {
                int read = inputStream.read(buffer);
                for (int i = 0; i < read; i++) {
                    buffer[i] ^= keyBytes[i % keyBytes.length];
                }
                outputStream.write(buffer);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("文件加/解密失败");
        }
    }

    public static String getFileSuffix(String name) {
        return name.substring(name.lastIndexOf("."));
    }
}
