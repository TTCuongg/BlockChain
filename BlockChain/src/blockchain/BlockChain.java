/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package blockchain;

import util.HashUtil;
import java.util.*;
/**
 *
 * @author truon
 */

public class BlockChain {

    private List<Block> chain = new ArrayList<>();

    public void addBlock(String fileName, String dataHash) throws Exception {
        Block block = new Block();

        block.fileName = fileName;
        block.dataHash = dataHash;
        block.timestamp = System.currentTimeMillis();
        block.previousHash = chain.isEmpty() ? "0" : chain.get(chain.size() - 1).hash;

        block.hash = calculateHash(block);

        chain.add(block);
    }

    private String calculateHash(Block block) throws Exception {
        String input = block.fileName + block.previousHash + block.dataHash + block.timestamp;
        return HashUtil.sha256(input.getBytes());
    }

    public boolean isValid() throws Exception {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            String recalculated = calculateHash(current);

            if (!current.hash.equals(recalculated)) return false;
            if (!current.previousHash.equals(previous.hash)) return false;
        }
        return true;
    }

    public Block findByFileName(String fileName) {
        for (Block b : chain) {
            if (b.fileName.equals(fileName)) {
                return b;
            }
        }
        return null;
    }

    public List<Block> getChain() {
        return chain;
    }
}
