# DES实验报告

## DES算法简介

1. DES算法为密码体制中的对称密码体制，又被称为美国数据加密标准。
2. DES是一个分组加密算法，典型的DES以64位为分组对数据加密，加密和解密用的是同一个算法。
3. 密钥长64位，密钥事实上是56位参与DES运算（第8、16、24、32、40、48、56、64位是校验位，使得每个密钥都有奇数个1），分组后的明文组和56位的密钥按位替代或交换的方法形成密文组。



## 关键代码

### 调用Java包进行DES加密

```java
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
```



### 数组的相关操作工具方法

```java
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

/**
 * 求两个字节数组的二进制中不同的位数
 *
 * @param m
 * @param n
 * @return 返回不同的位数的个数
 */
public static int countBitDiff(byte[] m, byte[] n) {
    if (m.length != n.length) {
        return -1;
    }

    byte[] ans = new byte[m.length];
    for (int i = 0; i < m.length; i++) {
        // 异或操作
        ans[i] = (byte) (m[i] ^ n[i]);
    }
    int count = 0;
    // 统计1的个数
    for (int i = 0; i < ans.length; i++) {
        while (ans[i] != 0) {
            ans[i] &= (byte) (ans[i] - 1);
            count++;
        }
    }
    return count;
}
```



## 测试截图

### 密钥固定，改变明文

![image-20211018104854913](https://tva1.sinaimg.cn/large/008i3skNgy1gvj9u1y7ndj60nu24etjk02.jpg)



### 明文固定，改变密钥

![image-20211018105122386](https://tva1.sinaimg.cn/large/008i3skNgy1gvj9wkku08j60m628ktk002.jpg)

