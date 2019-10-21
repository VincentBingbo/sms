package com.vincent.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DecryptUtil {

    public static String decrypt(String content, String aesKey) {
        String result = null;
        byte[] dst = decrypt4Aes(content, aesKey);
        if (null != dst) {
            result = new String(dst,StandardCharsets.UTF_8);
        }
        return result;
    }

    private static byte[] decrypt4Aes(String content, String aesKey) {
        byte[] src = base64decode(content);
        return mode(src, aesKey, false);
    }

    private static byte[] base64decode(String s) {
        if (s == null) return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(s);
        } catch (IOException e) {
            return null;
        }
    }

    private static String entrypt(String content, String key) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        byte[] bytesOut = mode(bytes, key, true);
        return base64encode(bytesOut);
    }

    private static byte[] mode(byte[] bytes, String key, boolean isEntry) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(isEntry ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, getSecretKey(key));
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKey getSecretKey(String aesKey) throws NoSuchAlgorithmException {
        byte[] byteKey = getKeyByStr(aesKey);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(byteKey);
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(secureRandom);
        return keyGenerator.generateKey();
    }

    private static byte[] getKeyByStr(String aesKey) {
        byte[] bRet = new byte[aesKey.length() / 2];
        for (int i = 0; i < aesKey.length() / 2; i++) {
            int itg = 16 * hexToDecimal(aesKey.charAt(2 * i)) + hexToDecimal(aesKey.charAt(2 * i + 1));
            bRet[i] = (byte) itg;
        }
        return bRet;
    }

    private static int hexToDecimal(char hexChar) {
        int outcome = 0;
        outcome = outcome * 16 + charToDecimal(hexChar);
        return outcome;
    }

    private static int charToDecimal(char c) {
        if (c >= 'A' && c <= 'F') {
            return 10 + c - 'A';
        }
        return c - '0';
    }

    private static String base64encode(byte[] src) {
        if (src == null) return null;
        return (new BASE64Encoder()).encode(src);
    }

    public static void main(String[] args) {
        System.out.println(entrypt("song123", "BFDD31FAA3494548BF71B02292B86CC"));
    }
}
