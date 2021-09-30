package com.wingsiwoo.www.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
public class EncryptUtil {
    public static void encryptFile(MultipartFile multipartFile, String privateKey, HttpServletResponse response) {
        byte[] keyBytes = privateKey.getBytes(StandardCharsets.UTF_8);
        byte[] buffer = new byte[1024];
        try (InputStream inputStream = multipartFile.getInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            while (inputStream.available() > 0) {
                int read = inputStream.read(buffer);
                byte[] encryptedBuffer = new byte[read];
                for (int i = 0; i < read; i++) {
                    encryptedBuffer[i] = (byte) (buffer[i] ^ keyBytes[i % keyBytes.length]);
                }
                outputStream.write(encryptedBuffer);
            }
            // 清空输出流
            outputStream.flush();
        } catch (IOException e) {
            throw new IllegalArgumentException("文件加/解密失败");
        }
    }

    public static String getFileSuffix(MultipartFile multipartFile) {
        String name = multipartFile.getOriginalFilename();
        assert name != null;
        return name.substring(name.lastIndexOf("."));
    }
}
