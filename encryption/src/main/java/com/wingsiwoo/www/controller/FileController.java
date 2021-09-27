package com.wingsiwoo.www.controller;

import com.wingsiwoo.www.po.EncryptBo;
import com.wingsiwoo.www.service.FileService;
import com.wingsiwoo.www.util.EncryptUtil;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/encryption")
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping("/encode")
    public ResponseEntity<byte[]> encode(@Validated EncryptBo encryptBo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDisposition(
                ContentDisposition
                        .builder("attachment")
                        .filename("加密文件" + EncryptUtil.getFileSuffix(encryptBo.getMultipartFile().getName()), StandardCharsets.UTF_8)
                        .build()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(httpHeaders)
                .body(fileService.encrypt(encryptBo));
    }

    @PostMapping("/decode")
    public ResponseEntity<byte[]> decode(@Validated @RequestBody EncryptBo encryptBo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDisposition(
                ContentDisposition
                        .builder("attachment")
                        .filename("解密文件" + EncryptUtil.getFileSuffix(encryptBo.getMultipartFile().getName()), StandardCharsets.UTF_8)
                        .build()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(httpHeaders)
                .body(fileService.encrypt(encryptBo));
    }
}
