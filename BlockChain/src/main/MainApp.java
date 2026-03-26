/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import service.*;

/**
 *
 * @author truon
 */
public class MainApp {
    public static void main(String[] args) throws Exception {

        KeyService keyService = new KeyService();
        BlockchainService blockchainService = new BlockchainService();

        CryptoService crypto = new CryptoService(keyService, blockchainService);

        crypto.encryptFile("data/input/test.txt",
                           "data/encrypted/test.enc",
                           "data/keys/test.key");

        byte[] decrypted = crypto.decryptFile(
                "data/encrypted/test.enc",
                "data/keys/test.key"
        );

        System.out.println("Decrypted: " + new String(decrypted));

        System.out.println("Blockchain valid: " + blockchainService.verify());
    }
}
