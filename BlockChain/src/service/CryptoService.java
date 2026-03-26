package service;

import crypto.aes.AESUtil;
import util.HashUtil;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class CryptoService {

    private KeyService keyService;
    private BlockchainService blockchainService;

    public CryptoService(KeyService keyService, BlockchainService blockchainService) {
        this.keyService = keyService;
        this.blockchainService = blockchainService;
    }

    // =========================
    // ENCRYPT FILE
    // =========================
    public void encryptFile(String inputPath, String outputPath, String keyPath) throws Exception {

        // 1. Đọc dữ liệu file gốc
        byte[] fileData = Files.readAllBytes(Paths.get(inputPath));

        // 2. Sinh AES key
        SecretKey aesKey = AESUtil.generateKey();

        // 3. Mã hóa dữ liệu bằng AES (có IV)
        byte[] encryptedData = AESUtil.encrypt(fileData, aesKey);

        // 4. Mã hóa AES key bằng RSA
        byte[] encryptedKey = keyService.encryptAESKey(aesKey.getEncoded());

        // 5. Lưu file mã hóa
        Files.write(Paths.get(outputPath), encryptedData);

        // 6. Lưu key đã mã hóa
        Files.write(Paths.get(keyPath), encryptedKey);

        // 7. Tính hash SHA-256 của file mã hóa
        String hash = HashUtil.sha256(encryptedData);

        // 8. Lấy tên file (để lưu blockchain)
        String fileName = Paths.get(outputPath).getFileName().toString();

        // 9. Lưu vào blockchain
        blockchainService.saveHash(fileName, hash);

        System.out.println("File encrypted successfully!");
    }

    // =========================
    // DECRYPT FILE + VERIFY
    // =========================
    public byte[] decryptFile(String encryptedPath, String keyPath) throws Exception {

        // 1. Đọc dữ liệu
        byte[] encryptedData = Files.readAllBytes(Paths.get(encryptedPath));
        byte[] encryptedKey = Files.readAllBytes(Paths.get(keyPath));

        // 2. Giải mã AES key bằng RSA
        byte[] aesKeyBytes = keyService.decryptAESKey(encryptedKey);
        SecretKey aesKey = AESUtil.fromBytes(aesKeyBytes);

        // 3. Tính hash hiện tại
        String currentHash = HashUtil.sha256(encryptedData);

        // 4. Lấy tên file
        String fileName = Paths.get(encryptedPath).getFileName().toString();

        // 5. Verify với blockchain
        boolean valid = blockchainService.verifyFile(fileName, currentHash);

        if (!valid) {
            System.out.println("⚠️ WARNING: File has been tampered!");
        } else {
            System.out.println("File integrity OK");
        }

        // 6. Giải mã dữ liệu
        byte[] decryptedData = AESUtil.decrypt(encryptedData, aesKey);

        return decryptedData;
    }
}