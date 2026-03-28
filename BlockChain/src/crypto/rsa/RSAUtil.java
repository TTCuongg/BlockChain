package crypto.rsa;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RSAUtil {

    // =========================
    // GENERATE KEY
    // =========================
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    // =========================
    // ENCRYPT
    // =========================
    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    // =========================
    // DECRYPT
    // =========================
    public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    // =========================
    // SAVE PUBLIC KEY
    // =========================
    public static void savePublicKey(PublicKey key, String path) throws Exception {
        Files.write(Paths.get(path), key.getEncoded());
    }

    // =========================
    // SAVE PRIVATE KEY
    // =========================
    public static void savePrivateKey(PrivateKey key, String path) throws Exception {
        Files.write(Paths.get(path), key.getEncoded());
    }

    // =========================
    // LOAD PUBLIC KEY
    // =========================
    public static PublicKey loadPublicKey(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    // =========================
    // LOAD PRIVATE KEY
    // =========================
    public static PrivateKey loadPrivateKey(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}