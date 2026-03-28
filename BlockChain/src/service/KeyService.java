package service;

import crypto.rsa.RSAUtil;

import java.io.*;
import java.security.*;

public class KeyService {

    private static final String PUBLIC_KEY_FILE = "data/keys/public.key";
    private static final String PRIVATE_KEY_FILE = "data/keys/private.key";

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyService() throws Exception {
        loadOrGenerateKeys();
    }

    private void loadOrGenerateKeys() throws Exception {

        File pubFile = new File(PUBLIC_KEY_FILE);
        File priFile = new File(PRIVATE_KEY_FILE);

        if (pubFile.exists() && priFile.exists()) {
            // Load key từ file
            publicKey = RSAUtil.loadPublicKey(PUBLIC_KEY_FILE);
            privateKey = RSAUtil.loadPrivateKey(PRIVATE_KEY_FILE);
        } else {
            // Tạo mới
            KeyPair keyPair = RSAUtil.generateKeyPair();

            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();

            RSAUtil.savePublicKey(publicKey, PUBLIC_KEY_FILE);
            RSAUtil.savePrivateKey(privateKey, PRIVATE_KEY_FILE);
        }
    }

    public byte[] encryptAESKey(byte[] aesKey) throws Exception {
        return RSAUtil.encrypt(aesKey, publicKey);
    }

    public byte[] decryptAESKey(byte[] encryptedKey) throws Exception {
        return RSAUtil.decrypt(encryptedKey, privateKey);
    }
}