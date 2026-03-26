/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import blockchain.BlockChain;

/**
 *
 * @author truon
 */
public class BlockchainService {

    private BlockChain blockchain = new BlockChain();

    public void saveHash(String hash) {
        blockchain.addBlock(hash);
    }

    public boolean verify() {
        return blockchain.isValid();
    }
}
