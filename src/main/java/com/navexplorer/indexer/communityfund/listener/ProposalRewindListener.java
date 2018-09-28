package com.navexplorer.indexer.communityfund.listener;

import com.navexplorer.indexer.block.event.BlockTransactionRewindEvent;
import com.navexplorer.indexer.communityfund.rewinder.ProposalRewinder;
import com.navexplorer.library.block.entity.BlockTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProposalRewindListener implements ApplicationListener<BlockTransactionRewindEvent> {
    @Autowired
    ProposalRewinder proposalRewinder;

    @Override
    public void onApplicationEvent(BlockTransactionRewindEvent event) {
        BlockTransaction transaction = event.getTransaction();

        proposalRewinder.revertUpdatedProposals(transaction);
        proposalRewinder.rewindProposal(transaction);
    }
}
