package com.wingsiwoo.www.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
public class EncryptUtil {
    public static boolean encryptFile(File file, String privateKey, File encryptedFile) {
        byte[] keyBytes = privateKey.getBytes(StandardCharsets.UTF_8);
        byte[] buffer = new byte[1024];
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = new FileOutputStream(encryptedFile)) {
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
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("文件加/解密失败");
        }
    }

    public static String getFileSuffix(File file) {
        String name = file.getName();
        return name.substring(name.lastIndexOf("."));
    }
}
