package com.xn.bmpos.manager.bmposmanager.util;


import java.security.MessageDigest;

/**
 * 加密工具类
 *
 * @author Administrator
 */
public class Tools {
    /**
     * 获取一个加密后的密码
     *
     * @param password
     * @return
     */
    public static String getPassword(String password) {

        return EncoderByMd5(password);

    }


    public static String EncoderByMd5(String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
