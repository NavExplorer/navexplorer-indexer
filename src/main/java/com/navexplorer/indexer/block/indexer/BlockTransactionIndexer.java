package com.navexplorer.indexer.block.indexer;

import com.navexplorer.indexer.block.event.BlockTransactionIndexedEvent;
import com.navexplorer.indexer.block.factory.BlockTransactionFactory;
import com.navexplorer.indexer.block.entity.Block;
import com.navexplorer.indexer.block.entity.BlockTransaction;
import com.navexplorer.indexer.block.repository.BlockRepository;
import com.navexplorer.indexer.block.service.BlockTransactionService;
import com.navexplorer.indexer.navcoin.service.NavcoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class BlockTransactionIndexer {
    private static final Logger logger = LoggerFactory.getLogger(BlockTransactionIndexer.class);

    @Autowired
    private NavcoinService navcoinService;

    @Autowired
    private BlockTransactionService blockTransactionService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private BlockTransactionFactory blockTransactionFactory;

    @Autowired
    private BlockRepository blockRepository;

    public void indexTransaction(String hash) {
        BlockTransaction transaction = blockTransactionFactory.createTransaction(navcoinService.getTransactionByHash(hash));
        logger.info(String.format("Saving block tx %s at height %d", transaction.getHash(), transaction.getHeight()));

        blockTransactionService.save(transaction);

        Block block = blockRepository.findByHeight(transaction.getHeight().longValue());

        applicationEventPublisher.publishEvent(new BlockTransactionIndexedEvent(this, block, transaction));
    }
}
