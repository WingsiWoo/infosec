package com.wingsiwoo.www;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * @author WingsiWoo
 * @date 2021/10/16
 */
public class DesUtil {
    public static byte[][] auxiliaryBytes;
    public static final int BYTE_64 = 64;
    public static final int REPEAT = 10;

    /**
     * DES加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static byte[] desEncrypt(byte[] data, String key) {
        try {
            SecureRandom secureRandom = new SecureRandom();
            // 创建密钥工厂
            DESKeySpec keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            // 把密钥转换为密钥规范
            SecretKey secureKey = factory.generateSecret(keySpec);
            // 获取cipher对象以完成加密操作
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            // 初始化cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, secureRandom);
            // 执行加密操作
            return cipher.doFinal(data);
        } catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException e) {
            throw new IllegalArgumentException("DES加密失败");
        }
    }

    /**
     * 初始化辅助数组 auxiliaryBytes是需要初始化的数组
     */
    public static void initAuxiliaryBytes() {
        //64代表的是含有1-64个1的串，8代表8个字节（64位）
        auxiliaryBytes = new byte[BYTE_64][8];
        // 10000000
        auxiliaryBytes[0][0] = -128;
        for (int i = 1; i < BYTE_64; i++) {
            System.arraycopy(auxiliaryBytes[i - 1], 0, auxiliaryBytes[i], 0, 8);
            int k = i / 8;
            int z = (i + 1) % 8;
            z = z == 0 ? 8 : z;
            auxiliaryBytes[i][k] ^= (byte) Math.pow(2, (8 - z));
        }
    }

    /**
     * 循环右移一个byte数组
     *
     * @param num   右移位数
     * @param bytes 数组
     */
    public static void bytesMoveToRight(int num, byte[] bytes) {
        // 每次循环右移一位，实现比较简单，但是效率比较慢
        for (int i = 0; i < num; i++) {
            // 记录最后一个字节的最低1位
            byte low = (byte) (bytes[bytes.length - 1] & 1);
            // 从第一个字节开始
            for (int j = 0; j < bytes.length; j++) {
                // 记录最低一位
                byte lowest = (byte) (bytes[j] & 1);
                // 右移一位，高位补0
                bytes[j] = (byte) (bytes[j] >> 1 & 127);
                // 把高位补上之前记录的最低1位
                bytes[j] = (byte) (bytes[j] ^ (low << 7));
                // 替换成新的最低1位
                low = lowest;
            }
        }
    }

    /**
     * 把byte数组类型转换为8位二进制类型字符串输出
     *
     * @param tByte 字节数组
     */
    public static void printTo8Binary(byte[] tByte) {
        for (byte value : tByte) {
            String tString = Integer.toBinaryString((value & 0xFF) + 0x100).substring(1);
            System.out.print(tString + " ");
        }
        System.out.println();
    }
}
