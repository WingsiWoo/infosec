package com.wingsiwoo.www.controller;

import com.wingsiwoo.www.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api")
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping("/encrypt")
    public void encode(@NotNull(message = "文件不能为空") @RequestParam MultipartFile multipartFile,
                       @NotEmpty(message = "密钥不能为空") @RequestParam String privateKey,
                       HttpServletResponse response) {
        fileService.encrypt(multipartFile, privateKey, response);
    }
}
