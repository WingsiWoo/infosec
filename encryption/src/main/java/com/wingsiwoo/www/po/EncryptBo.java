package com.wingsiwoo.www.po;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author WingsiWoo
 * @date 2021/9/26
 */
@Data
public class EncryptBo {
    @NotEmpty(message = "密钥不能为空")
    private String privateKey;

    @NotNull(message = "待解密文件不能为空")
    private MultipartFile multipartFile;
}
