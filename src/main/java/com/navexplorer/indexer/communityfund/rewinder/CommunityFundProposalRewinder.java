package com.navexplorer.indexer.communityfund.rewinder;

import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.communityfund.entity.Proposal;
import com.navexplorer.library.communityfund.repository.CommunityFundProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityFundProposalRewinder {
    private static final Logger logger = LoggerFactory.getLogger(CommunityFundProposalRewinder.class);

    @Autowired
    private CommunityFundProposalRepository communityFundProposalRepository;

    public void rewindProposal(BlockTransaction transaction) {
        Proposal proposal = communityFundProposalRepository.findOneByHash(transaction.getHash());

        if (proposal != null) {
            communityFundProposalRepository.delete(proposal);

            logger.info("Community proposal deleted: " + proposal.getHash());
        };
    }
}
