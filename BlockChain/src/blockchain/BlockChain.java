/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package blockchain;
import java.util.*;
/**
 *
 * @author truon
 */
public class BlockChain {

    private List<Block> chain = new ArrayList<>();

    public void addBlock(String dataHash) {
        Block block = new Block();

        block.dataHash = dataHash;
        block.timestamp = System.currentTimeMillis();
        block.previousHash = chain.isEmpty() ? "0" : chain.get(chain.size()-1).hash;

        block.hash = calculateHash(block);

        chain.add(block);
    }

    private String calculateHash(Block block) {
        return Integer.toHexString(
            (block.previousHash + block.dataHash + block.timestamp).hashCode()
        );
    }

    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i-1);

            if (!current.previousHash.equals(previous.hash)) {
                return false;
            }
        }
        return true;
    }

    public List<Block> getChain() {
        return chain;
    }
}
