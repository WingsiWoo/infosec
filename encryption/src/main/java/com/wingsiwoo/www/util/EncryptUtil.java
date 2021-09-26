package com.wingsiwoo.www.util;

import com.wingsiwoo.www.po.EncryptBo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
public class EncryptUtil {
    public static byte[] encode(MultipartFile multipartFile, String privateKey) {
        try(InputStream inputStream = multipartFile.getInputStream()) {
            int read = -1;
            while((read = inputStream.read()) != -1) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkPassword(String userName, String password) {

    }

    public static String getPrivateKey(String userName) {

    }
}
