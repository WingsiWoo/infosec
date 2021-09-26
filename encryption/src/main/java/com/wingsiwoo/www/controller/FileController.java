package com.wingsiwoo.www.controller;

import com.wingsiwoo.www.po.EncryptBo;
import com.wingsiwoo.www.po.LoginBo;
import com.wingsiwoo.www.result.Result;
import com.wingsiwoo.www.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@RestController
@RequestMapping("/api/encryption")
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping("/login")
    public Result<LoginBo> login(@Validated @RequestBody LoginBo loginBo) {
        return Result.operateSuccess(fileService.login(loginBo));
    }

    @PostMapping("/encode")
    public ResponseEntity<byte[]> encode(@Validated EncryptBo encryptBo) {

    }

    @PostMapping("/decode")
    public ResponseEntity<byte[]> decode(@Validated @RequestBody EncryptBo encryptBo) {

    }

    /**
     * 根据用户名获取唯一的密钥
     * @param userName
     * @return
     */
    @PostMapping("/getPrivateKey")
    public Result<String> getPrivateKey(@NotEmpty(message = "用户名不能为空") @RequestParam String userName) {

    }
}
