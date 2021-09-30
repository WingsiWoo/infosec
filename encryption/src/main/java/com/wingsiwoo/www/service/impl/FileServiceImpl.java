package com.wingsiwoo.www.service.impl;

import com.wingsiwoo.www.service.FileService;
import com.wingsiwoo.www.util.EncryptUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    public void encrypt(MultipartFile multipartFile, String privateKey, HttpServletResponse response) {
        String fileName = "encryptedFile" + LocalDateTime.now().getSecond() + EncryptUtil.getFileSuffix(multipartFile);

        try {
            // 设置响应头
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, "UTF-8") +
                    ";filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Content-Type", "application/octet-stream");
            // 进度条所需响应头
            response.addHeader("Accept-Ranges", "bytes");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("编码不支持");
        }

        EncryptUtil.encryptFile(multipartFile, privateKey, response);
    }
}
