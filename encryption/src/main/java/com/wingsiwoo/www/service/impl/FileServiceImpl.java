package com.wingsiwoo.www.service.impl;

import com.wingsiwoo.www.service.FileService;
import com.wingsiwoo.www.util.EncryptUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public void encrypt(final MultipartFile multipartFile, final String privateKey, final HttpServletResponse response) {
        String fileName = "encryptedFile" + EncryptUtil.getFileSuffix(multipartFile);

        try {
            // 设置响应头
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, "UTF-8") +
                    ";filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("Content-Type", "application/octet-stream");
            // 进度条所需响应头
            response.addHeader("Accept-Ranges", "bytes");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        EncryptUtil.encryptFile(multipartFile, privateKey, response);
    }
}
