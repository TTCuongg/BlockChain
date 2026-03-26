/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import crypto.aes.AESUtil;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author truon
 */
public class CryptoService {

    private KeyService keyService;
    private BlockchainService blockchainService;

    public CryptoService(KeyService keyService, BlockchainService blockchainService) {
        this.keyService = keyService;
        this.blockchainService = blockchainService;
    }

    public void encryptFile(String inputPath, String outputPath, String keyPath) throws Exception {

        byte[] fileData = Files.readAllBytes(Paths.get(inputPath));

        // AES
        SecretKey aesKey = AESUtil.generateKey();
        byte[] encryptedData = AESUtil.encrypt(fileData, aesKey);

        // RSA encrypt AES key
        byte[] encryptedKey = keyService.encryptAESKey(aesKey.getEncoded());

        // Save
        Files.write(Paths.get(outputPath), encryptedData);
        Files.write(Paths.get(keyPath), encryptedKey);

        // Blockchain
        String hash = Integer.toHexString(java.util.Arrays.hashCode(encryptedData));
        blockchainService.saveHash(hash);
    }

    public byte[] decryptFile(String encryptedPath, String keyPath) throws Exception {

        byte[] encryptedData = Files.readAllBytes(Paths.get(encryptedPath));
        byte[] encryptedKey = Files.readAllBytes(Paths.get(keyPath));

        // RSA decrypt AES key
        byte[] aesKeyBytes = keyService.decryptAESKey(encryptedKey);

        SecretKey aesKey = AESUtil.fromBytes(aesKeyBytes);

        return AESUtil.decrypt(encryptedData, aesKey);
    }
}
