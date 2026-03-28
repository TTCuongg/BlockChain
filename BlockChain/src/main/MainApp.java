package main;

import service.*;

public class MainApp {

    public static void main(String[] args) throws Exception {

        KeyService keyService = new KeyService();
        BlockchainService blockchainService = new BlockchainService();

        blockchainService.loadFromFile();

        CryptoService crypto = new CryptoService(keyService, blockchainService);

        // ===== MODE =====
        boolean DO_ENCRYPT = false;

        if (DO_ENCRYPT) {
            crypto.encryptFile(
                    "data/input/test.txt",
                    "data/encrypted/test.enc",
                    "data/keys/test.key"
            );
        }

        byte[] decrypted = crypto.decryptFile(
                "data/encrypted/test.enc",
                "data/keys/test.key"
        );

        System.out.println("Decrypted: " + new String(decrypted));

        System.out.println("Blockchain valid: " + blockchainService.verifyChain());
    }
}