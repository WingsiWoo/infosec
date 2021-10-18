package com.wingsiwoo.www;

import static com.wingsiwoo.www.DesUtil.*;

/**
 * @author WingsiWoo
 * @date 2021/10/16
 */
public class DesApplication {
    public static void main(String[] args) {
        //初始化辅助数组
        DesUtil.initAuxiliaryBytes();
        //初始明文
        String word = "wingsiwoo";
        DesUtil.printTo8Binary(word.getBytes());
        //初始密钥
        String key = "3219004950";
        DesUtil.printTo8Binary(key.getBytes());
        //加密
        byte[] result = DesUtil.desEncrypt(word.getBytes(), key);
        DesUtil.printTo8Binary(result);

        // 统计DES算法在密钥固定情况，输入明文改变1位、2位，。。。64位时。输出密文位数改变情况
        changePlainText(word, key, result);
        // 统计DES算法在明文固定情况，输入密钥改变1位、2位，。。。64位时。输出密文位数改变情况
        changeKey(key, result);
    }

    /**
     * 统计DES算法在密钥固定情况，输入明文改变1位、2位，。。。64位时。输出密文位数改变情况
     *
     * @param word 明文
     * @param key  密钥
     */
    private static void changePlainText(String word, String key, byte[] result) {
        //改变明文位数，改变1-64位
        for (int i = 1; i <= BYTE_64; i++) {
            System.out.print("明文改变" + i + "位, ");
            //原始明文
            byte[] origin = word.getBytes();
            //改变位数
            int digits = 0;
            //10种情况取平均
            for (int j = 0; j < REPEAT; j++) {
                //当前明文
                byte[] now = new byte[8];
                //用辅助数组进行变位（i-1位）
                for (int k = 0; k < origin.length; k++) {
                    now[k] = (byte) (origin[k] ^ auxiliaryBytes[i - 1][k]);
                }
                //右移一下辅助数组，供下种情况使用
                DesUtil.bytesMoveToRight(1, auxiliaryBytes[i - 1]);
                //加密
                byte[] encryptedBytes = DesUtil.desEncrypt(now, key);
                //和原始密文比较，记录改变位数
                if (encryptedBytes != null) {
                    digits += DesUtil.countBitDiff(encryptedBytes, result);
                }
            }
            System.out.println(" 重复10次，密文平均改变了" + (double) digits / 10.0 + "位");
        }
    }

    /**
     * 统计DES算法在明文固定情况，输入密钥改变1位、2位，。。。64位时。输出密文位数改变情况
     *
     * @param key    密钥
     * @param result 密文
     */
    private static void changeKey(String key, byte[] result) {

    }

}
