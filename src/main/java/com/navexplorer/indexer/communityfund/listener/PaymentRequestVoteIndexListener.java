package com.navexplorer.indexer.communityfund.listener;

import com.navexplorer.indexer.block.event.BlockIndexedEvent;
import com.navexplorer.indexer.communityfund.indexer.PaymentRequestVoteIndexer;
import com.navexplorer.library.block.entity.Block;
import com.navexplorer.library.block.service.BlockTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentRequestVoteIndexListener implements ApplicationListener<BlockIndexedEvent> {
    @Autowired
    private PaymentRequestVoteIndexer paymentRequestVoteIndexer;

    @Autowired
    private BlockTransactionService blockTransactionService;

    @Override
    public void onApplicationEvent(BlockIndexedEvent event) {
        Block block = event.getBlock();

        paymentRequestVoteIndexer.indexPaymentRequestVotes(block, blockTransactionService.getByBlockHash(block.getHash()));
    }
}
