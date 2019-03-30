package com.navexplorer.indexer.block.indexer;

import com.navexplorer.indexer.block.entity.OutputType;
import com.navexplorer.indexer.block.event.BlockIndexedEvent;
import com.navexplorer.indexer.block.event.OrphanedBlockEvent;
import com.navexplorer.indexer.block.exception.*;
import com.navexplorer.indexer.block.factory.BlockFactory;
import com.navexplorer.indexer.block.service.BlockIndexingActiveService;
import com.navexplorer.indexer.exception.IndexerException;
import com.navexplorer.indexer.block.entity.Block;
import com.navexplorer.indexer.block.entity.BlockTransaction;
import com.navexplorer.indexer.block.repository.BlockTransactionRepository;
import com.navexplorer.indexer.block.service.BlockService;
import com.navexplorer.indexer.block.service.BlockTransactionService;
import com.navexplorer.indexer.navcoin.service.NavcoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlockIndexer {
    private static final Logger logger = LoggerFactory.getLogger(BlockIndexer.class);

    @Autowired
    private BlockIndexingActiveService blockIndexingActiveService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private NavcoinService navcoinService;

    @Autowired
    private BlockTransactionRepository blockTransactionRepository;

    @Autowired
    private BlockTransactionService blockTransactionService;

    @Autowired
    private BlockTransactionIndexer blockTransactionIndexer;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private BlockFactory blockFactory;

    public void indexAllBlocks() {
        mustBeActive();

        boolean indexing = true;
        Block bestBlock = null;

        while (indexing) {
            try {
                bestBlock = indexBlocks(bestBlock);
            } catch (IndexerException e) {
                indexing = false;
            }
        }
    }

    public Block indexBlocks(Block bestBlock) throws IndexerException {
        try {
            bestBlock = bestBlock != null ? bestBlock : blockService.getBestBlock();
            long bestHeight = bestBlock == null ? 0L : bestBlock.getHeight();
            logger.info("Indexing block at height {}", bestHeight+1);

            Double previousBalance = bestBlock == null ? 0.0 : bestBlock.getBalance();

            org.navcoin.response.Block apiBlock = navcoinService.getBlockByHeight(bestHeight + 1);
            if (apiBlock == null) {
                throw new ReachedBestBlockException("Best block is " + bestHeight);
            }

            if (blockIsOrphan(bestBlock, apiBlock)) {
                throw new OrphanBlockException(String.format("Building on a orphan block at height: %s", bestBlock.getHeight()));
            }

            return indexBlock(apiBlock, previousBalance);
        } catch (OrphanBlockException e) {
            applicationEventPublisher.publishEvent(new OrphanedBlockEvent(this));
        }

        return null;
    }

    private Block indexBlock(org.navcoin.response.Block apiBlock, Double previousBalance) {
        Block block = blockFactory.createBlock(apiBlock);
        blockService.save(block);

        apiBlock.getTx().forEach(blockTransactionIndexer::indexTransaction);

        updateFeesAndSpendForBlock(block);
        updateStakingInfo(block);
        block.setBalance(previousBalance + (block.getStake() != null ? block.getStake() : 0.0) + block.getCFundPayout());

        blockService.save(block);

        applicationEventPublisher.publishEvent(new BlockIndexedEvent(this, block));

        return block;
    }

    private void updateFeesAndSpendForBlock(Block block) {
        blockTransactionService.getByHeight(block.getHeight()).forEach(transaction -> {
            block.setFees(block.getFees() + transaction.getFees());

            if (transaction.isSpend()) {
                block.setSpend(block.getSpend() + transaction.getOutputAmount());
            }

            if (transaction.isCoinbase()) {
                transaction.getOutputs().forEach(o -> {
                    if (o.getType() == OutputType.PUBKEYHASH) {
                        block.setCFundPayout(block.getCFundPayout() + o.getAmount());
                    }
                });
            }
        });
    }

    private void updateStakingInfo(Block block) {
        Optional<BlockTransaction> transactionOptional = blockTransactionRepository.findByBlockHash(block.getHash())
                .stream()
                .filter(bt -> bt.getStake() > 0.0)
                .findFirst();
        if (!transactionOptional.isPresent()) {
            logger.info("Didnt find staking transaction");
            return;
        }


        BlockTransaction transaction = transactionOptional.get();
        logger.info("Found block {}", transaction.getHash());

        block.setStake(transaction.getStake());

        transaction.getOutputs().stream().filter(o -> o.getAddresses().size() > 0).findFirst()
                .ifPresent(output -> block.setStakedBy(output.getAddresses().get(0)));

        logger.info("Didnt find staking address on output");
        if (block.getStakedBy() == null) {
            // could find an address on the inputs so check the outputs
            transaction.getInputs().stream().filter(i -> i.getAddresses().size() > 0).findFirst()
                    .ifPresent(input -> block.setStakedBy(input.getAddresses().get(0)));
        }

        if (block.getStakedBy() == null) {
            logger.info("Didnt find staking address on input");
        }
    }

    private Boolean blockIsOrphan(Block bestBlock, org.navcoin.response.Block apiBlock) {
        return bestBlock != null && !apiBlock.getPreviousblockhash().equals(bestBlock.getHash());
    }

    private void mustBeActive() {
        if (!blockIndexingActiveService.isActive()) {
            throw new BlockIndexingNotActiveException("Block indexing is disabled");
        }
    }
}
