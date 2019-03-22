package com.navexplorer.indexer.block.service;

import com.navexplorer.indexer.block.entity.Block;
import com.navexplorer.indexer.block.query.DirectionalIdInterface;
import com.navexplorer.indexer.block.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockService implements DirectionalIdInterface {
    @Autowired
    private BlockRepository blockRepository;

    public Block save(Block block) {
        return blockRepository.save(block);
    }

    public Block getBestBlock() {
        Block block = blockRepository.findFirstByOrderByHeightDesc();
        if (block != null) {
            block.setBest(true);
        }

        return block;
    }
}
