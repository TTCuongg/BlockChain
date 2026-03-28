package main;

import service.*;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) throws Exception {

        KeyService keyService = new KeyService();
        BlockchainService blockchainService = new BlockchainService();

        blockchainService.loadFromFile();

        CryptoService crypto = new CryptoService(keyService, blockchainService);

        Scanner sc = new Scanner(System.in);

        System.out.println("===== SYSTEM MENU =====");
        System.out.println("1. Encrypt file");
        System.out.println("2. Decrypt file");
        System.out.print("Choose: ");

        int choice = sc.nextInt();

        switch (choice) {

            case 1:
                crypto.encryptFile(
                        "data/input/test.txt",
                        "data/encrypted/test.enc",
                        "data/keys/test.key"
                );
                break;

            case 2:
                byte[] decrypted = crypto.decryptFile(
                        "data/encrypted/test.enc",
                        "data/keys/test.key"
                );
                System.out.println("Decrypted: " + new String(decrypted));
                break;

            default:
                System.out.println("Invalid choice!");
        }

        System.out.println("Blockchain valid: " + blockchainService.verifyChain());
    }
}