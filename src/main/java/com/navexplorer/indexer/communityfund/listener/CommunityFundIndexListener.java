package com.navexplorer.indexer.communityfund.listener;

import com.navexplorer.indexer.block.event.BlockTransactionIndexedEvent;
import com.navexplorer.indexer.communityfund.indexer.CommunityFundProposalIndexer;
import com.navexplorer.library.block.entity.BlockTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CommunityFundIndexListener implements ApplicationListener<BlockTransactionIndexedEvent> {
    @Autowired
    CommunityFundProposalIndexer communityFundProposalIndexer;

    @Override
    public void onApplicationEvent(BlockTransactionIndexedEvent event) {
        BlockTransaction transaction = event.getTransaction();

        communityFundProposalIndexer.indexProposal(transaction);
        communityFundProposalIndexer.updateProposals(transaction);
    }
}
