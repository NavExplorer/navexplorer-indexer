package com.navexplorer.indexer.block.listener;

import com.navexplorer.indexer.block.event.BlockTransactionIndexedEvent;
import com.navexplorer.indexer.block.indexer.BlockTransactionProposalVoteIndexer;
import com.navexplorer.indexer.block.service.PreviousInputService;
import com.navexplorer.indexer.communityfund.indexer.CommunityFundProposalIndexer;
import com.navexplorer.library.block.entity.BlockTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BlockTransactionIndexedListener implements ApplicationListener<BlockTransactionIndexedEvent> {
    @Autowired
    PreviousInputService previousInputService;

    @Autowired
    BlockTransactionProposalVoteIndexer blockProposalVoteIndexer;

    @Autowired
    CommunityFundProposalIndexer communityFundProposalIndexer;

    @Override
    public void onApplicationEvent(BlockTransactionIndexedEvent event) {
        BlockTransaction transaction = event.getTransaction();

        previousInputService.updateTransaction(transaction);

        if (transaction.isStaking()) {
            blockProposalVoteIndexer.indexProposalVotes(transaction);
        }
    }
}
