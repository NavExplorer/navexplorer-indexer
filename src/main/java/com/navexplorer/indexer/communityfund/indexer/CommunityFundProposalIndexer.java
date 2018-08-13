package com.navexplorer.indexer.communityfund.indexer;

import com.navexplorer.indexer.communityfund.factory.CommunityFundProposalFactory;
import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.communityfund.entity.CommunityFundProposal;
import com.navexplorer.library.communityfund.repository.CommunityFundProposalRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityFundProposalIndexer {
    private static final Logger logger = LoggerFactory.getLogger(CommunityFundProposalIndexer.class);

    @Autowired
    private CommunityFundProposalFactory communityFundProposalFactory;

    @Autowired
    private CommunityFundProposalRepository communityFundProposalRepository;

    public void indexProposal(BlockTransaction transaction) {
        if (transaction.getVersion() != 4) {
            return;
        }

        try {
            CommunityFundProposal proposal = communityFundProposalFactory.createProposal(transaction);
            communityFundProposalRepository.save(proposal);

            logger.info("Community proposal saved: " + proposal.getHeight());

        } catch (Exception e) {
            logger.error("Unable to index community fund proposal for tx: " + transaction.getHash());
        }
    }
}
