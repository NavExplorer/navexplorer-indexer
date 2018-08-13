package com.navexplorer.indexer.communityfund.listener;

import com.navexplorer.indexer.block.event.BlockTransactionRewindEvent;
import com.navexplorer.indexer.communityfund.rewinder.CommunityFundProposalRewinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CommunityFundRewindListener implements ApplicationListener<BlockTransactionRewindEvent> {
    @Autowired
    CommunityFundProposalRewinder communityFundProposalRewinder;

    @Override
    public void onApplicationEvent(BlockTransactionRewindEvent event) {
        communityFundProposalRewinder.rewindProposal(event.getTransaction());
    }
}
