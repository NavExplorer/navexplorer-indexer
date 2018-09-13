package com.navexplorer.indexer.communityfund.rewinder;

import com.navexplorer.indexer.communityfund.factory.CommunityFundProposalFactory;
import com.navexplorer.library.block.entity.BlockTransaction;
import com.navexplorer.library.block.entity.BlockTransactionType;
import com.navexplorer.library.communityfund.entity.BlockCycle;
import com.navexplorer.library.communityfund.entity.Proposal;
import com.navexplorer.library.communityfund.entity.ProposalState;
import com.navexplorer.library.communityfund.repository.CommunityFundProposalRepository;
import com.navexplorer.library.communityfund.service.BlockCycleService;
import com.navexplorer.library.navcoin.service.NavcoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityFundProposalRewinder {
    private static final Logger logger = LoggerFactory.getLogger(CommunityFundProposalRewinder.class);

    @Autowired
    private CommunityFundProposalRepository communityFundProposalRepository;

    @Autowired
    private NavcoinService navcoinService;

    @Autowired
    private CommunityFundProposalFactory communityFundProposalFactory;

    public void rewindProposal(BlockTransaction transaction) {
        Proposal proposal = communityFundProposalRepository.findOneByHash(transaction.getHash());

        if (proposal != null) {
            communityFundProposalRepository.delete(proposal);

            logger.info("Community proposal deleted: " + proposal.getHash());
        }
    }


    public void revertUpdatedProposals(BlockTransaction transaction) {
        if (!transaction.getType().equals(BlockTransactionType.STAKING)) {
            return;
        }

        revertProposalsByState(ProposalState.PENDING);
        revertProposalsByState(ProposalState.ACCEPTED);
        revertProposalsByState(ProposalState.REJECTED);
        revertProposalsByState(ProposalState.EXPIRED);
        revertProposalsByState(ProposalState.PENDING_FUNDS);
    }

    private void revertProposalsByState(ProposalState state) {
        communityFundProposalRepository.findAllByStateOrderByIdDesc(state).forEach(proposal -> {
            communityFundProposalFactory.updateProposal(proposal, navcoinService.getProposal(proposal.getHash()));
            communityFundProposalRepository.save(proposal);

            logger.info(String.format("Community proposal updated: %s %s", proposal.getState(), proposal.getHash()));
        });
    }
}
