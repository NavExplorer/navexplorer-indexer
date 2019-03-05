package com.navexplorer.indexer.block.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navexplorer.indexer.block.entity.BlockTransaction;
import com.navexplorer.indexer.block.exception.BlockTransactionNotFoundException;
import com.navexplorer.indexer.block.query.DirectionalIdInterface;
import com.navexplorer.indexer.block.repository.BlockTransactionRepository;
import com.navexplorer.indexer.navcoin.service.NavcoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockTransactionService implements DirectionalIdInterface {
    @Autowired
    private BlockTransactionRepository blockTransactionRepository;

    @Autowired
    private NavcoinService navcoinService;

    public BlockTransaction save(BlockTransaction blockTransaction) {
        return blockTransactionRepository.save(blockTransaction);
    }

    public List<BlockTransaction> getByBlockHash(String blockHash) {
        return blockTransactionRepository.findByBlockHash(blockHash);
    }

    public BlockTransaction getOneByHash(String hash) throws BlockTransactionNotFoundException {
        BlockTransaction transaction = blockTransactionRepository.findFirstByHash(hash);

        if (transaction != null) {
            transaction.setRaw(getRawTransactionData(hash));
        } else {
            throw new BlockTransactionNotFoundException(String.format("Block transaction not found: %s", hash));
        }

        return transaction;
    }

    public List<BlockTransaction> getByHeight(Long height) {
        return blockTransactionRepository.findByHeight(height);
    }

    private String getRawTransactionData(String hash) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(navcoinService.getTransactionByHash(hash));
        } catch (Exception e) {
            return "";
        }
    }
}
