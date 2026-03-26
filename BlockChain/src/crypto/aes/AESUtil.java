/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crypto.aes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 *
 * @author truon
 */
public class AESUtil {

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    public static byte[] encrypt(byte[] data, SecretKey key) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        byte[] encrypted = cipher.doFinal(data);

        // Ghép IV + data
        byte[] result = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

        return result;
    }

    public static byte[] decrypt(byte[] encryptedData, SecretKey key) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] iv = new byte[16];
        System.arraycopy(encryptedData, 0, iv, 0, 16);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

        byte[] actualData = new byte[encryptedData.length - 16];
        System.arraycopy(encryptedData, 16, actualData, 0, actualData.length);

        return cipher.doFinal(actualData);
    }

    public static SecretKey fromBytes(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, "AES");
    }
}
