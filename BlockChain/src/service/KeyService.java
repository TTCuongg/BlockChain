/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import crypto.rsa.RSAUtil;
import java.security.*;
/**
 *
 * @author truon
 */
public class KeyService {

    private KeyPair keyPair;

    public KeyService() throws Exception {
        this.keyPair = RSAUtil.generateKeyPair();
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public byte[] encryptAESKey(byte[] aesKey) throws Exception {
        return RSAUtil.encrypt(aesKey, getPublicKey());
    }

    public byte[] decryptAESKey(byte[] encryptedKey) throws Exception {
        return RSAUtil.decrypt(encryptedKey, getPrivateKey());
    }
}
