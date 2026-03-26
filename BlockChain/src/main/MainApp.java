package main;

import service.*;

public class MainApp {

    public static void main(String[] args) throws Exception {

        // 1. Khởi tạo service
        KeyService keyService = new KeyService();
        BlockchainService blockchainService = new BlockchainService();

        // 2. Load blockchain từ file (QUAN TRỌNG)
        blockchainService.loadFromFile();

        CryptoService crypto = new CryptoService(keyService, blockchainService);

        // 3. ENCRYPT
        crypto.encryptFile(
                "data/input/test.txt",
                "data/encrypted/test.enc",
                "data/keys/test.key"
        );

        // 4. DECRYPT + VERIFY
        byte[] decrypted = crypto.decryptFile(
                "data/encrypted/test.enc",
                "data/keys/test.key"
        );

        // 5. In kết quả
        System.out.println("Decrypted: " + new String(decrypted));

        // 6. Verify toàn bộ blockchain
        boolean validChain = blockchainService.verifyChain();
        System.out.println("Blockchain valid: " + validChain);
    }
}