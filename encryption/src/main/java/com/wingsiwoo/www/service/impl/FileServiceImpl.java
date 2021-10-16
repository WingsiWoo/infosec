package com.wingsiwoo.www.service.impl;

import com.wingsiwoo.www.service.FileService;
import com.wingsiwoo.www.util.EncryptUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String encrypt(File file, String privateKey) {
        File encryptedFile = new File(file.getName() + "-encrypted" + EncryptUtil.getFileSuffix(file));

        try {
           if(!encryptedFile.exists()) {
               encryptedFile.createNewFile();
           }
           if(EncryptUtil.encryptFile(file, privateKey, encryptedFile)) {
               return encryptedFile.getAbsolutePath();
           }
        } catch (IOException e) {
            throw new IllegalArgumentException("创建文件" + encryptedFile.getName() + "失败");
        }
        return null;
    }
}
