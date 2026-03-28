/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import blockchain.*;
import config.AppConfig;

import java.io.*;


/**
 *
 * @author truon
 */
public class BlockchainService {

    private BlockChain blockchain = new BlockChain();

    public void saveHash(String fileName, String hash) throws Exception {
        blockchain.addBlock(fileName, hash);
        saveToFile();
    }

    public boolean verifyChain() throws Exception {
        return blockchain.isValid();
    }

    public boolean verifyFile(String fileName, String currentHash) {
    Block block = blockchain.findLatestByFileName(fileName);

    if (block == null) return false;

    return block.dataHash.equals(currentHash);
    }

    private void saveToFile() throws Exception {

        FileWriter writer = new FileWriter(AppConfig.BLOCKCHAIN_FILE);

        for (Block b : blockchain.getChain()) {
            writer.write(b.fileName + "|" +
                         b.previousHash + "|" +
                         b.dataHash + "|" +
                         b.timestamp + "|" +
                         b.hash + "\n");
        }

        writer.close();
    }

    public void loadFromFile() throws Exception {

        File file = new File(AppConfig.BLOCKCHAIN_FILE);
        if (!file.exists()) return;

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {

            String[] parts = line.split("\\|");

            Block b = new Block();
            b.fileName = parts[0];
            b.previousHash = parts[1];
            b.dataHash = parts[2];
            b.timestamp = Long.parseLong(parts[3]);
            b.hash = parts[4];

            blockchain.getChain().add(b);
        }

        reader.close();
    }
}