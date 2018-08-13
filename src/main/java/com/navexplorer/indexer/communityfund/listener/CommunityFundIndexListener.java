package com.navexplorer.indexer.communityfund.listener;

import com.navexplorer.indexer.block.event.BlockTransactionIndexedEvent;
import com.navexplorer.indexer.communityfund.indexer.CommunityFundProposalIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CommunityFundIndexListener implements ApplicationListener<BlockTransactionIndexedEvent> {
    @Autowired
    CommunityFundProposalIndexer communityFundProposalIndexer;

    @Override
    public void onApplicationEvent(BlockTransactionIndexedEvent event) {
        communityFundProposalIndexer.indexProposal(event.getTransaction());
    }
}
