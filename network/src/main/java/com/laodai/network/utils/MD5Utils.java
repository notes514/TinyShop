package com.laodai.network.utils;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 09:39 2020-03-10
 * @ Description：MD5加密工具类
 * @ Modified By：
 * @Version: ：1.0
 */
public class MD5Utils {

    /**
     * HexUtil类实现Hex(16进制字符串)和字节数组的互转
     *
     * @param str 字符串
     * @return string
     */
    private static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return HexUtil.encode(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 加盐(salt)MD5加密
     *
     * @param password 密码
     * @return 加密后的MD5
     */
    public static String getSaltMd5(String password) {
        //生成一个16位数的随机数
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        //生成最终加密的盐(salt)
        String salt = sb.toString();
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            //在结果中的每三位用中间位保存salt值
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }

    /**
     * 验证加盐(salt)后是否和原文一致
     *
     * @param password 密码
     * @param md5Str md5字符串
     * @return boolean
     */
    public static boolean getSaltverifyMd5(String password, String md5Str) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5Str.charAt(i);
            cs1[i / 3 * 2 + 1] = md5Str.charAt(i + 2);
            cs2[i / 3] = md5Str.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(String.valueOf(cs1));
    }

    public static class HexUtil {

        /**
         * 字节流转成十六进制表示
         *
         * @param bytes 流
         * @return string
         */
        public static String encode(byte[] bytes) {
            String hexStr = "";
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                hexStr = Integer.toHexString(aByte & 0xFF);
                //每个字节由两个字符表示，位数不够，高位补0
                sb.append((hexStr.length() == 1) ? "0" + hexStr : hexStr);
            }
            return sb.toString().trim();
        }

        /**
         * 字符串转成字节流
         *
         * @param str string
         * @return byte[]
         */
        public static byte[] decode(String str) {
            int m = 0, n = 0;
            //每两个字符描述一个字节
            int byteLen = str.length() / 2;
            byte[] ret = new byte[byteLen];
            for (int i = 0; i < byteLen; i++) {
                m = i * 2 + 1;
                n = m + 1;
                int intVal = Integer.decode("0x" + str.substring(i * 2, m) + str.substring(m, n));
                ret[i] = (byte) intVal;
            }
            return ret;
        }

    }
}
